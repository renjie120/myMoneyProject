package myOwnLibrary.flashchart;

public enum LabelDisplay {
	WRAP {
		public String toString() {
			return "WRAP";
		}
	},
	STAGGER {
		public String toString() {
			return "STAGGER";
		}
	},
	ROTATE  {
		public String toString() {
			return "ROTATE ";
		}
	},
	NONE  {
		public String toString() {
			return "NONE ";
		}
	};
}
