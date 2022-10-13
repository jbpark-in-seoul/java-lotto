package lotto.view;

import lotto.domain.Lotto;
import lotto.domain.LottoNumber;
import validator.InputValidator;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.Collectors;

import static validator.InputValidator.isContains;
import static validator.InputValidator.isNumeric;


public class Input {
    private final static Scanner SCANNER = new Scanner(System.in);
    private final static String SEPARATOR_OF_LOTTO_TICKET_NUMBER = ",";

    public int amount() {
        System.out.println("구매하실 금액을 입력해주세요.(1장당 1000원 / 최대 100장 구매 가능)");
        String input = SCANNER.nextLine();
        if (isNumeric(input)) {
            return Integer.parseInt(input);
        }
        throw new InputMismatchException("입력하신 금액이 올바르지 않습니다. 숫자만 입력 가능합니다.");
    }

    public Lotto winningNumberOfLastWeek() {
        System.out.println("지난 주 당첨 번호를 입력해주세요.(ex.3,14,16,19,22,24)");
        String input = SCANNER.nextLine();

        if (isContains(input, SEPARATOR_OF_LOTTO_TICKET_NUMBER) && Arrays.stream(input.split(SEPARATOR_OF_LOTTO_TICKET_NUMBER))
                .allMatch(InputValidator::isNumeric)) {

            return new Lotto(Arrays.stream(input.split(SEPARATOR_OF_LOTTO_TICKET_NUMBER))
                    .mapToInt(Integer::parseInt)
                    .mapToObj(LottoNumber::lottoNumber)
                    .collect(Collectors.toList()));
        }
        throw new InputMismatchException("당첨 번호는 숫자만 입력 가능하며, 콤마로 구분해야 합니다.");
    }

    public LottoNumber bonusNumberOfLastWeek() {
        System.out.println("보너스 볼을 입력해주세요. (입력 가능 숫자:1 ~ 45)");
        String input = SCANNER.nextLine();
        if (isNumeric(input)) {
            return LottoNumber.lottoNumber(Integer.parseInt(input));
        }
        throw new InputMismatchException("보너스 번호는 숫자만 입력 가능합니다.");
    }
}
