Feature: Basic Arithmetic

  Background: A Calculator
    Given a calculator I just turned on

  @testing
  Scenario Outline: Several additions1
    When I add <a> and <b>
    Then the result is <c>
    Then make me: "cry"

  Examples: Single digits1
    | a | b | c  |
    | 1 | 2 | 4  |
    | 3 | 7 | 11 |

  Scenario Outline: Several addition2
    When I add <a> and <b>
    Then the result is <c>
    Then make me: "laught"

    Examples: Single digits2
      | a | b | c  |
      | 1 | 2 | 3  |
      | 3 | 7 | 10 |

  Scenario Outline: Several addition3
    When I add <a> and <b>
    Then the result is <c>
    Then make me: "fuck"

    Examples: Single digits3
      | a | b | c  |
      | 1 | 2 | 3  |
      | 3 | 7 | 10 |

  Scenario Outline: Several addition4
    When I add <a> and <b>
    Then the result is <c>

    Examples: Single digits4
      | a | b | c  |
      | 1 | 2 | 3  |
      | 3 | 7 | 10 |