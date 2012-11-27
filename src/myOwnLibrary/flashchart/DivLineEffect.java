package myOwnLibrary.flashchart;

public enum DivLineEffect {
	EMBOSS  {
		public String toString() {
			return "EMBOSS";
		}
	},
	BEVEL  {
		public String toString() {
			return "BEVEL";
		}
	},
	NONE  {
		public String toString() {
			return "NONE";
		}
	};
}
