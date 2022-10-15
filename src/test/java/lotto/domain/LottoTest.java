package lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static lotto.domain.LottoNumber.lottoNumbers;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LottoTest {

    @Test
    @DisplayName("생성자 의존성 주입 - 로또 번호")
    void constructorDI() {
        assertThat(new Lotto(lottoNumbers()).lottoNumbers().size()).isEqualTo(45);
    }

    @Test
    @DisplayName("생성자 의존성 주입 - 당첨 번호 길이 예외")
    void constructorDIExceptionForWinningNumberLength() {
        assertThatThrownBy(() -> new Lotto(LottoNumber.lottoNumbers().subList(0, 7))).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("발급 로또 조회")
    void lottoNumber() {
        LottoMachine lotto = new LottoMachine(lottoNumbers());
        assertThat(lotto.issue(new Payment(1000)).get(0).lottoNumbers()).hasSizeLessThanOrEqualTo(6);
        assertThat(lotto.issue(new Payment(1000)).get(0).lottoNumbers()).hasSizeGreaterThanOrEqualTo(0);
    }

    @RepeatedTest(100)
    @DisplayName("발급 로또 티켓, 당첨 티켓 비교")
    void compareNumber() {
        List<Lotto> lottoTickets = new LottoMachine(lottoNumbers()).issue(new Payment(1000));

        Lotto winningNumber = new WinningNumber(new Lotto(new ArrayList<>(LottoNumber.lottoNumbers().subList(0, 6))), LottoNumber.lottoNumber(7)).winningNumber();
        List<Integer> matchingCounts = lottoTickets.stream()
                .map(ticket -> ticket.compareNumber(winningNumber))
                .collect(Collectors.toList());

        assertThat(matchingCounts).hasSize(1);
        assertThat(matchingCounts.get(0)).isLessThanOrEqualTo(6);
        assertThat(matchingCounts.get(0)).isGreaterThanOrEqualTo(0);
    }
}
