package LL1;

public class parse {

	private int lookahead;//��ŵ�ǰɨ�赥�ʵ��ֱ���
	
	//S->F=E
	public void ParseS() {
		switch(lookahead) {
		case 13://������
		case 100://id
		case 99://����
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
    //F->(E)|����|����
    public void ParseF() {
    	switch(lookahead) {
		case 13://������
			ParseE();
			MatchToken(14);//(),14)
			break;
		case 100://id
			MatchToken(100);
			break;
		case 99://����
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
	//B->*FB|��
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
