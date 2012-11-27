package myOwnLibrary.flashchart;

/** 
* @ClassName: LabelHAlign 
* @Description: VLine标签中的label的水平位置
* @author 419723443@qq.com
* @date Jan 22, 2010 11:21:30 AM 
*  
*/
public enum LabelHAlign {
	LEFT {
		public String toString() {
			return "left";
		}
	},
	CENTER {
		public String toString() {
			return "center";
		}
	},
	RIGHT  {
		public String toString() {
			return "right";
		}
	};
}
