/******************************************************
 * A multi-line Javadoc-like comment about my grammar *
 ******************************************************/
grammar Pascal;
@header {
    package pequenopascal.parser;
    import pascal.util.*;
}
//program "string";
//var y : integer; 
//begin 
//print "";



//end
//begin 

//end

program : PROGRAM STR EOL block  #programStart          
        ;


block   : varDeclapart?       
          proceduredeclapart?
          function?             
          block1                 #blocks
        ;


types : INTEGER                #typeInteger
        | FLOAT                #typeFloat
        | CHAR                 #typeChar     
        | STRING               #typeString 
        | INT                  #typeInt     
        | BOOLEAN              #typeBoolean  
        ;
proceduredeclapart: PROCEDURE ID OPEN (varDeclapart)* CLOSE EOL var? #proced
        ;   


function : FUNCTION ID OPEN (varDeclapart)+ CLOSE ATTR types EOL #funct
        ;


block1 : BEGIN (stmt)* END   #bl0ck1
        ;


var : ID (var1)+             #varVar 
    ;

var1 :  ID (',' ID)* ATTR types EOL  #v4r 
    ;

varDeclapart : VAR var1     #varDeclap
    ;

stmt    : print EOL             #stmtPrint
        | read  EOL             #stmtRead
        | attr  EOL             #stmtAttr
        | expr  EOL             #stmtExpr
        | cond                  #stmtCond
        | write EOL             #stmtwrite
        | while1 EOL             #stmtwhile1    
        | for1 EOL               #stmtfor
        ;
//ATÉ AQUI TUDO NA PAZ

read    : READ OPEN ID CLOSE          #readVar
        ;

for1 : FOR OPEN attr? EOL condExpr EOL attr? CLOSE block bl=block at=attr #f0r1
        ;

write : WRITE OPEN STR CLOSE        #writeSTR
      | WRITE OPEN expr CLOSE       #writeEXP
      | WRITELN OPEN STR CLOSE      #writelnSTR
      | WRITELN OPEN expr CLOSE     #writelnEXP
      ;

while1 : WHILE condExpr DO x=block1;


cond    : IF '('condExpr')' b1=block1                  #ifStmt
        | IF '('condExpr')' b1=block1 ELSE b2=block1    #ifElseStmt 
        ;

condExpr: expr                                              #condExpresion
        | expr relop=('>'|'<'|'=='|'>='|'<='|'!=') expr     #condRelOp
        ;

attr    : ID IS expr     #attrExpr
        ;

expr    : expr1 '+' expr    #exprPlus
        | expr1 '-' expr    #exprMinus
        | expr1             #expr1Empty
        ;
expr1   : expr2 '*' expr    #expr1Mult
        | expr2 '/' expr    #expr1Div
        | expr2             #expr2Empty
        ;

expr2   : '(' expr ')'      #expr2Par
        | NUM               #expr2Num
        | VAR               #expr2Var
        | STR               #expr2Str
        ;




print   : PRINT STR         #printStr
        | PRINT expr        #printExpr
        ;


// TOKENS


FOR      : 'for';
INTEGER  : 'integer';
BOOLEAN  : 'boolean' ;
FLOAT    : 'float' ;
STRING   : 'string' ;
TRUE     : 'true';
FALSE    : 'false';
INT      : 'int';
CHAR     : 'char';
DO       : 'do';


// TOKENS

VAR      : 'v''a''r';
DOT      : '.';
OR       : 'or';
AND      : 'and';
NOT      : 'not';
THEN     : 'then';
OF       : 'of'; 
WHILE    : 'while';
BEGIN    : 'begin';
END      : 'end';
WRITE    : 'write';
ARRAY    : 'array';
PROGRAM  : 'program';
PROCEDURE: 'procedure';

// TOKENS
IF      : 'if';
ELSE    : 'else';
OPENSB  : '[';
CLOSSB  : ']';
MQMQ    : '<>';
GT      : '>' ;
LT      : '<' ;
ATTR    : ':';
EQ      : '==';
PTI     : ':=';
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
NUM     : [0-9]+ ;
FUNCTION: 'function';
ID      : [a-zA-Z][a-zA-Z0-9_]*;
STR     : '"' ('\\' ["\\] | ~["\\\r\n])* '"';
WS      : [\n\r \t]+ -> skip;
READLN  : 'readln';
WRITELN   : 'writeln';
