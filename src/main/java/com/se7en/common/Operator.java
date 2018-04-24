package com.se7en.common;

import java.math.BigDecimal;

/**
 * The Enum Operator.
 * 操作符
 */
public enum Operator {

	lt{
		@Override
		public boolean operate(final String opLeft,final String opRight) {
		
			return computeBoolean(opLeft, opRight) < 0;
		}
	},
	lt_eq{
		@Override
		public boolean operate(final String opLeft,final String opRight) {
			
			return computeBoolean(opLeft, opRight) <= 0;
		}
	},
	gt{
		@Override
		public boolean operate(final String opLeft,final String opRight) {
			
			return computeBoolean(opLeft, opRight) > 0;
		}
	},
	gt_eq{
		@Override
		public boolean operate(final String opLeft,final String opRight) {
		
			return computeBoolean(opLeft, opRight) >= 0;
		}
	},
	eq{
		@Override
		public boolean operate(final String opLeft,final String opRight) {
		
			return computeBoolean(opLeft, opRight) == 0;
		}
	};
	
	public boolean operate(final String opLeft,final String opRight){
		return false;
	}
	
	protected int computeBoolean(final String opLeft,final String opRight){
		return new BigDecimal(opLeft).compareTo(new BigDecimal(opRight));
	}
}
