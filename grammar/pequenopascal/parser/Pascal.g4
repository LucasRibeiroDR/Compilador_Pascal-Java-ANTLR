/******************************************************
 * A multi-line Javadoc-like comment about my grammar *
 ******************************************************/
grammar Pascal;
@header {
    package pequenopascal.parser;
    import pascal.util.*;
}
@members{
String traducao = "";
String aux = "";
char open = 123;
char close = 125;
String num = "";
String num2 = "";



}

program : PROGRAM STR EOL block  {traducao+="#include<iostream>\n";
                                  traducao+="#include<string>\n";
                                  traducao+="using namespace std;\n";
                                  traducao+="int main()"+open+"\n"; 
 
}#programStart          
        ;


block   : vardeclablock?       
          proceduredeclapart?
          function?           
          block1                 #blocks
        ;

var : ID (varDeclapart)+             #varVar 
    ;
vardeclablock: VAR varDeclapart*
    ;    
varDeclapart :  ID (',' ID)* ATTR simpletype EOL {traducao+=$ID.text+";"+"\n";}   #v4r 
        | ID {aux+=$ID.text;} (',' ID)* ':=' arraytype      #v4rArrayTp
        | ID                                #varID 
        | ID '['expr']'                     #indexedVar
    ;


simpletype : INTEGER      {traducao+="int ";}          #typeInteger
        | FLOAT           {traducao+="float ";}    #typeFloat   
        | STRING          {traducao+="string ";}     #typeString 
        | BOOLEAN         {traducao+="boolean ";}    #typeBoolean  
        ;
arraytype: ARRAY '['indexRange']' OF simpletype {traducao+=aux+"["+num2+"-"+num+"];"+"\n";}  #arrayTypeIDR
        ;

indexRange: NUM {num+=$NUM.text;} '..' NUM    {num2+=$NUM.text;}    #idrange
           ;

proceduredeclapart: 
        ;   

function : 
        
;


block1 : BEGIN  (stmt)* END'.'  #bl0ck1
        | BEGIN  (stmt)* END  {{traducao+="return 0;"+close;}}{System.out.println(traducao);}{SalvarArquivo.get(traducao);}   #bl0ck2
        ;


stmt    : write              #stmtwrite
        | read              #stmtRead
        | attr              #stmtAttr
        | expr               #stmtExpr
        | cond               #stmtCond
        | while1             #stmtwhile1    
        | for1                #stmtfor   
        ;

while1 : WHILE {traducao+="while(";} OPEN condExpr CLOSE {traducao+=")";} DO {traducao+=open+"\n";} x=block1 {traducao+=close;}    #whili1;    

for1 :  FOR {traducao+="for(";;} OPEN attr? {traducao+=";";} EOL condExpr {traducao+=";";}EOL b2 = attr CLOSE {traducao+=")"+open;}  b1=block1 {traducao+=close;} #f0r1
        ;

cond    : IF {traducao+="if(";} OPEN condExpr CLOSE {traducao+=")"+open;} THEN b1=block1  {traducao+=close;}                #ifStmt
        | IF {traducao+="if(";} OPEN condExpr CLOSE {traducao+=")"+open;} THEN b1=block1  {traducao+=close;} ELSE {traducao+="else"+open;} b2=block1    #ifElseStmt 
        ;

condExpr: expr                                              #condExpresion
        | expr relop=('>'|'<'|'=='|'>='|'<='|'!=') {traducao+=$relop.text;} expr     #condRelOp
        ;


write : WRITE  STR     {traducao+="cout<<"+$STR.text+"<<endl;"+"\n";}    #writeSTR
      | WRITE   {traducao+="cout<<";}   expr {traducao+="<<endl;"+"\n";}         #write1
      
      ;

read    : READ  ID  {traducao+="cin>>"+$ID.text+";"+"\n";}       #readVar
        ;

attr    :   varDeclapart  {traducao+=$varDeclapart.text+"=";} ':=' expr  {traducao+=";"+"\n";} #attrExpr
        
        
        ;



expr    : expr1 '+' {traducao+="+";} expr    #exprPlus
        | expr1 '-' {traducao+="-";} expr    #exprMinus
        | expr1             #expr1Empty
        ;
expr1   : expr2 '*' {traducao+="*";} expr    #expr1Mult
        | expr2 '/' {traducao+="/";}expr    #expr1Div
        | expr2             #expr2Empty
        ;

expr2   : OPEN expr CLOSE       #expr2Par
        | NUM    {traducao+=$NUM.text;}          #expr2Num
        | ID     {traducao+=$ID.text;}         #expr2Var
        | STR    {traducao+=$STR.text;}          #expr2Str
        | bool              #bool1
        ;

bool : TRUE   {traducao+=$TRUE.text;} #exprTrue
     | FALSE  {traducao+=$FALSE.text;} #exprFalse
     ; 





print   : PRINT STR         #printStr
        | PRINT expr        #printExpr
        ;


// TOKENS
FALSE   : 'false';
TRUE    : 'true';
THEN    : 'then';
STRING  : 'string';
WHILE   : 'while';
FOR     : 'for';
DO      : 'do';
BEGIN   : 'begin';
END     : 'end';
WRITE   : 'write';
PROGRAM : 'program';
VAR     : 'var';
INTEGER : 'integer';
FLOAT   : 'float';
BOOLEAN : 'boolean';
ARRAY   : 'array';
OF      : 'of';
IF      : 'if';
ELSE    : 'else';
GT      : '>' ;
LT      : '<' ;
EQ      : '==';
GE      : '>=';
LE      : '<=';
NE      : '!=';
PLUS    : '+' ;
MINUS   : '-' ;
MULT    : '*' ;
DIV     : '/' ;
OPEN    : '(' ;
CLOSE   : ')' ;
OPEN_BL : '{' ;
CLOSE_BL: '}' ;
IS      : '=' ;
EOL     : ';' ;
PRINT   : 'print' ;
READ    : 'read' ;
NUM     : [0-9]+('.'[0-9]+)? ;
ID      : [a-zA-Z][a-zA-Z0-9_]*('['[0-9]']')?;
STR     : '"' ('\\' ["\\] | ~["\\\r\n])* '"';
WS      : [\n\r \t]+ -> skip;
ATTR    : ':=';




//ARRAY    : 'array';


//OPENSB  : '[';
///CLOSSB  : ']';
//MQMQ    : '<>';











