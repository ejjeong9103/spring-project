package com.estsoft.springproject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class JUnitTest {
    @Test
    public void test() {
        // given
        int a = 1;
        int b = 2;
        // when : 검증하고싶은 메소드(코드) 호출
        int sum = a + b;


        // then : when절에서 실행한 결과 검증
//        Assertions.assertEquals(3, sum); // 기대값, 로직
        Assertions.assertThat(sum).isEqualTo(3);
        Assertions.assertThat(sum).isOdd();
//        Assertions.assertThat(sum).isEven(); 짝수가 아니므로 failed

    }
}
