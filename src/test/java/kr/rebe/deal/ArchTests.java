package kr.rebe.deal;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.junit.ArchTest;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;
/*
* 패키지 의존성 확인
* */
@AnalyzeClasses(packagesOf = DealApplication.class)
public class ArchTests {

    /*
    * 순환참조 없어야 한다.
    * */
    @ArchTest
    ArchRule freeOfCycles = slices().matching("..rebe.(*)..")
            .should().beFreeOfCycles();

    /*
    * Controller는 Service와 Respository에 접근할 수 있다.
    * */
    @ArchTest
    ArchRule controllerClassRule = classes().that().haveSimpleNameEndingWith("Controller")
            .should().accessClassesThat().haveSimpleNameEndingWith("Service")
            .orShould().accessClassesThat().haveSimpleNameEndingWith("Repository");

    /*
    * Repository는 Controller 또는 Service를 참조하면 안된다.
    * */
    @ArchTest
    ArchRule repositoryClassRule = noClasses().that().haveSimpleNameEndingWith("Repository")
            .should().accessClassesThat().haveSimpleNameEndingWith("Controller")
            .orShould().accessClassesThat().haveSimpleNameEndingWith("Service");
}
