> Source Code repository is now streamlined and online.

---


> ## What is Carma ##

Carma is a tool which validates the quality of your product by analyzing your test cases. It clearly identifies gaps in your test cases and reports the conditions which certainly require additional and/or enhanced test cases. It exceeds the power of plain code coverage tools as such tools rely on a technique which does not seriously help in identifying weak test cases. Just imagine a load of test cases without any assertions. The coverage results would signal a excellent coverage but still erroneous behavior in your code cannot be detected at all. Carma uses a more mature technique and cannot be tricked in this way as the tests are not verified their-self, but instead the sensitivity for errors in your product is determined. That makes Carma much more powerful than coverage tools. If you want to know more about the technique used by Carma visit [Mutation Testing (Wikipedia)](http://en.wikipedia.org/wiki/Mutation_testing) and [Fault Injection (Wikipedia)](http://en.wikipedia.org/wiki/Fault_injection).

This project was started in spring 2007 and was previously located here: http://retroduction.org

> ## Target Audience ##
Carma helps out
  * Java developers and teams with strong unit testing background to keep the test harness proper and reduce gaps in the test harness
  * Quality Managers which need to make sure that a certain code quality level is guaranteed

> ## Features ##
  * Reports show untested or weakly tested code based on statements including column information (in contrast to other coverage tools which report on line level).
  * Error injection is performed on byte code level for higher performance.
  * Apache Maven 2 and Apache Ant support for easy integration. Carma focuses on easy integration with existing build environments.
  * An optional command line interface which can be used to integrate with arbitrary build environments using a scripting approach.

> ## Download and Documentation ##

Please check the website at [Retroduction.org](http://retroduction.org) for binary releases and documentation until everything is migrated to Google Projects.
