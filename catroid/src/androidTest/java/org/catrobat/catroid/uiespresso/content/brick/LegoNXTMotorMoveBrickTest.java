/*
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2017 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * An additional term exception under section 7 of the GNU Affero
 * General Public License, version 3, is available at
 * http://developer.catrobat.org/license_additional_term
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.catrobat.catroid.uiespresso.content.brick;

import android.support.test.runner.AndroidJUnit4;

import org.catrobat.catroid.R;
import org.catrobat.catroid.content.bricks.LegoNxtMotorMoveBrick;
import org.catrobat.catroid.ui.ScriptActivity;
import org.catrobat.catroid.uiespresso.annotations.Flaky;
import org.catrobat.catroid.uiespresso.content.brick.utils.BrickTestUtils;
import org.catrobat.catroid.uiespresso.content.brick.utils.SpinnerUtils;
import org.catrobat.catroid.uiespresso.util.BaseActivityInstrumentationRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static org.catrobat.catroid.uiespresso.content.brick.utils.BrickTestUtils.checkIfBrickAtPositionShowsString;
import static org.catrobat.catroid.uiespresso.content.brick.utils.FormulaTextFieldUtils.enterValueInFormulaTextFieldOnBrickAtPosition;
import static org.catrobat.catroid.uiespresso.content.brick.utils.SpinnerUtils.checkIfValuesAvailableInSpinnerOnBrick;
import static org.catrobat.catroid.uiespresso.content.brick.utils.SpinnerUtils.clickSelectCheckSpinnerValueOnBrick;

@RunWith(AndroidJUnit4.class)
public class LegoNXTMotorMoveBrickTest {
	private int brickPosition;

	@Rule
	public BaseActivityInstrumentationRule<ScriptActivity> baseActivityTestRule = new
			BaseActivityInstrumentationRule<>(ScriptActivity.class, true, false);

	@Before
	public void setUp() throws Exception {
		brickPosition = 1;
		int startVelocity = 10;
		BrickTestUtils.createProjectAndGetStartScript("legoNXTMotorMoveBrickTest")
				.addBrick(new LegoNxtMotorMoveBrick(LegoNxtMotorMoveBrick.Motor.MOTOR_A, startVelocity));
		baseActivityTestRule.launchActivity(null);
	}

	@Test
	@Flaky(3)
	public void testLegoNXTMoveMotorBrick() {
		int velocityToChange = 20;
		checkIfBrickAtPositionShowsString(0, R.string.brick_when_started);
		checkIfBrickAtPositionShowsString(brickPosition, R.string.nxt_brick_motor_move);

		SpinnerUtils.checkIfSpinnerOnBrickAtPositionShowsString(R.id.lego_motor_action_spinner, brickPosition, R.string.nxt_motor_a);
		clickSelectCheckSpinnerValueOnBrick(R.id.lego_motor_action_spinner, brickPosition, R.string.nxt_motor_b);

		List<Integer> spinnerValuesResourceIds = Arrays.asList(
				R.string.nxt_motor_a,
				R.string.nxt_motor_b,
				R.string.nxt_motor_c,
				R.string.nxt_motor_b_and_c);
		checkIfValuesAvailableInSpinnerOnBrick(spinnerValuesResourceIds, R.id.lego_motor_action_spinner, brickPosition);

		enterValueInFormulaTextFieldOnBrickAtPosition(velocityToChange, R.id.motor_action_speed_edit_text,
				brickPosition);
	}
}
