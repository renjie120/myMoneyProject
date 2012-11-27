package myOwnLibrary.flashchart;

/**
 * @ClassName: DefaultNumberScale
 * @Description: 默认的数字格式化的三种选择
 * @author 419723443@qq.com
 * @date Jan 22, 2010 10:14:28 AM
 * 
 */
public enum DefaultNumberScale {
	/*
	 * 38 was converted to 38s 150 was converted to 2.50min 11050 was converted
	 * to 3.07hr 334345 was converted to 3.87 day 1334345 was converted to
	 * 2.21wk
	 */
	S {
		public String toString() {
			return "s";
		}
	},
	/*
	 * 8 bits = 1 Byte 1024 bytes = 1 KB 1024 KB = 1 MB 1024 MB = 1 GB 1024 GB =
	 * 1 TB
	 */
	BITS {
		public String toString() {
			return "bits";
		}
	},
	/*
	 * 12 inches = 1 feet 3 feet = 1 yard 1760 yards = 1 mile
	 */
	INCHES {
		public String toString() {
			return "inches";
		}
	}
}
