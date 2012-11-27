package myOwnLibrary.flashchart;

/** 
* @ClassName: LogoPosition 
* @Description: chart中的logoPosition属性枚举
* @author 419723443@qq.com
* @date Jan 20, 2010 5:06:06 PM 
*  
*/
public enum LogoPosition {
	TL {
		public String toString() {
			return "TL";
		}
	},
	TR {
		public String toString() {
			return "TR";
		}
	},
	BR {
		public String toString() {
			return "BR";
		}
	},
	CC {
		public String toString() {
			return "CC";
		}
	},
	BL {
		public String toString() {
			return "BL";
		}
	};
}