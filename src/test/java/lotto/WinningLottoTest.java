package lotto;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import lotto.domain.LottoNum;
import lotto.domain.WinningLotto;
import lotto.exception.DuplicateBonusNumberException;
import lotto.exception.ErrorMessage;

class WinningLottoTest {

	static Stream<Arguments> providerOfWinningLottoHasDuplicatedBonusNumber() {
		return Stream.of(
			Arguments.arguments(Stream.of(1, 2, 3, 4, 5, 6).map(LottoNum::of).collect(Collectors.toList()),
				LottoNum.of(6)),
			Arguments.arguments(Stream.of(1, 2, 3, 4, 5, 7).map(LottoNum::of).collect(Collectors.toList()),
				LottoNum.of(5)),
			Arguments.arguments(Stream.of(1, 2, 3, 4, 7, 8).map(LottoNum::of).collect(Collectors.toList()),
				LottoNum.of(4)),
			Arguments.arguments(Stream.of(1, 2, 3, 7, 8, 9).map(LottoNum::of).collect(Collectors.toList()),
				LottoNum.of(3)),
			Arguments.arguments(Stream.of(1, 2, 7, 8, 9, 10).map(LottoNum::of).collect(Collectors.toList()),
				LottoNum.of(2)),
			Arguments.arguments(Stream.of(1, 7, 8, 9, 10, 11).map(LottoNum::of).collect(Collectors.toList()),
				LottoNum.of(1))
		);
	}

	@DisplayName("보너스 번호가 6개 번호 중 하나와 중복되면 예외를 던진다.")
	@ParameterizedTest
	@MethodSource("providerOfWinningLottoHasDuplicatedBonusNumber")
	void Should_Throw_Duplicate_Exception_When_Winning_Lotto_Has_Duplicated_Bonus_Number(List<LottoNum> numbers,
		LottoNum bonusNumber) {
		assertThatThrownBy(() -> {
			WinningLotto winningLotto = new WinningLotto(numbers, bonusNumber);
		}).isInstanceOf(DuplicateBonusNumberException.class)
			.hasMessage(ErrorMessage.BONUS_NUMBER_MUST_NOT_BE_DUPLICATED.getMessage());
	}
}
