package LL1;

public class parse {

	private int lookahead;//存放当前扫描单词的种别码
	
	//S->F=E
	public void ParseS() {
		switch(lookahead) {
		case 13://左括号
		case 100://id
		case 99://常数
			ParseF();
			MatchToken(41);//(=,41)
			ParseE();
			break;
		default: error();
		}
	}
	//E->TA
    public void ParseE() {
		if(lookahead==100||lookahead==99||lookahead==13) {
			ParseT();
			ParseA();
		}
	}
    //F->(E)|变量|常数
    public void ParseF() {
    	switch(lookahead) {
		case 13://左括号
			ParseE();
			MatchToken(14);//(),14)
			break;
		case 100://id
			MatchToken(100);
			break;
		case 99://常数
			MatchToken(100);
			break;
		default: error();
		}
	}
    //T->FB
	public void ParseT() {
		if(lookahead==100||lookahead==99||lookahead==13) {
			ParseF();
			ParseB();
		}
	}
	
	public void ParseA() {
		
	}
	//B->*FB|ε
    public void ParseB() {
		if(lookahead==35) {
			//(*,35)
			MatchToken(35);
			ParseF();
			ParseB();
		}
		else if(lookahead==1) {
			//+,1
		}
			
	}
	
	public void MatchToken(int expected) {
		
	}
	public void error() {
		
	}
}
