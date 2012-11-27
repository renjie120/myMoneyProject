package myOwnLibrary.flashchart;

/** 
* @ClassName: LabelVAlign 
* @Description: VLine标签中的label的垂直位置
* @author 419723443@qq.com
* @date Jan 22, 2010 11:22:03 AM 
*  
*/
public enum LabelVAlign {
	TOP {
		public String toString() {
			return "top";
		}
	},
	MIDDLE {
		public String toString() {
			return "middle";
		}
	},
	BOTTOM  {
		public String toString() {
			return "bottom";
		}
	};
}
