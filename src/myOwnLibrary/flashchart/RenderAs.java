package myOwnLibrary.flashchart;

public enum RenderAs {
	COLUMN {
		public String toString() {
			return "COLUMN";
		}
	},
	AREA  {
		public String toString() {
			return "AREA";
		}
	},
	LINE   {
		public String toString() {
			return "LINE";
		}
	};
}
