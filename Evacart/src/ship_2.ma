[top]
components : ship

[ship]
type : cell
width : 20
height : 20
delay : transport
defaultDelayTime : 20
border : nowrapped 
neighbors : ship(-2,0)
neighbors : ship(-1,-1) ship(-1,0) ship(-1,1)
neighbors : ship(0,-1)  ship(0,0) ship(0,1) ship(0,2)
neighbors : ship(1,-1)  ship(1,0) ship(1,1)

initialvalue : 0
initialrowvalue :  0     11111112111111111111
initialrowvalue :  1     10000000000000000001
initialrowvalue :  2     10001100000001111101
initialrowvalue :  3     11111111111101111101
initialrowvalue :  4     10100000000100000001
initialrowvalue :  5     10000000000000000001
initialrowvalue :  6     10111111111101111101
initialrowvalue :  7     10000000000000000001
initialrowvalue :  8     20111111111101111102
initialrowvalue :  9     10000000000000000001
initialrowvalue :  10    10111111011111111101
initialrowvalue :  11    10000000000000000101
initialrowvalue :  12    10101111000000000101
initialrowvalue :  13    11101111111100000101
initialrowvalue :  14    10000011111100000001
initialrowvalue :  15    10000000000001111101
initialrowvalue :  16    10000000000000000101
initialrowvalue :  17    10111111111111111101
initialrowvalue :  18    10000000000000000001
initialrowvalue :  19    11111111121111111111

localtransition : ship-rule


[ship-rule]
rule : {3 + randInt(1)} 0 { (0,0) = 0 and (1,0) > 1 and (1,0) < 11}
rule : {5 + randInt(1)} 0 { (0,0) = 0 and (0,1) > 1 and (0,1) < 11}
rule : {7 + randInt(1)} 0 { (0,0) = 0 and (-1,0) > 1 and (-1,0) < 11} 
rule : {9 + randInt(1)} 0 { (0,0) = 0 and (0,-1) > 1 and (0,-1) < 11}

rule : 4  100 { (0,0) = 3 and ( (0,1) = 10 or (-1,0) = 4 or (0,-1) = 6 )}
rule : 6  100 { (0,0) = 5 and ( (1,0) = 8 or (-1,0) = 4 or (0,-1) = 6 )}
rule : 8  100 { (0,0) = 7 and ( (1,0) = 8 or (0,1) = 10 or (0,-1) = 6 )}
rule : 10 100 { (0,0) = 9 and ( (1,0) = 8 or (0,1) = 10 or (-1,0) = 4 )}

rule : 3 100 { (0,0) = 4 and (1,0) = 2}
rule : 9 100 { (0,0) = 10 and (0,-1) = 2}
rule : 7 100 { (0,0) = 8 and (-1,0) = 2}
rule : 5 100 { (0,0) = 6 and (0,1) = 2}

rule : 3  100 { (0,0) = 4 and odd((1,0)) }
rule : 9  100 { (0,0) = 10 and odd((0,-1)) and (-1,-1) != 4 }
rule : 7  100 { (0,0) = 8 and odd((-1,0)) and (-2,0) != 4 and (-1,1) != 10 }		
rule : 5  100 { (0,0) = 6 and odd((0,1)) and (-1,1) != 4 and (0,2) != 10 and (1,1) != 8 }

rule : {(0,0)} 100 { t } 
