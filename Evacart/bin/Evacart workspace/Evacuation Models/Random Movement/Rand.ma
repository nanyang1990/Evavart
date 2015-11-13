%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Rand.ma
% Author: Ryan Lawler
%
% Description: This model describes random movement of people in a
% building.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

[top]
components : Rand

[Rand]
type : cell
dim : (17, 17)
delay : transport
defaultDelayTime : 1000
border : nowrapped

%5x5 Neighborhood
neighbors : Rand(-2,-2) Rand(-2,-1) Rand(-2,0) Rand(-2,1) Rand(-2,2)
neighbors : Rand(-1,-2) Rand(-1,-1) Rand(-1,0)  Rand(-1,1) Rand(-1,2)
neighbors : Rand(0,-2)  Rand(0,-1)  Rand(0,0)   Rand(0,1)  Rand(0,2)
neighbors : Rand(1,-2)  Rand(1,-1)  Rand(1,0)   Rand(1,1)  Rand(1,2)
neighbors : Rand(2,-2)  Rand(2,-1)  Rand(2,0)   Rand(2,1)  Rand(2,2)

%Initialize Floor Map
initialvalue : -1
initialCellsValue : Rand.val

%Floor plane
zone : floor_plane 	{ (0,0)..(16,16) }

localtransition : standard-rule

[floor_plane]
%Exit Building
rule : 0 1000 {(0,0)>0 and (0,0)<90 and ((0,1)=900 or (1,1)=900 or (1,0)=900 or (1,-1)=900 or (0,-1)=900 or (-1,-1)=900 or (-1,0)=900 or (-1,1)=900)}

%Get Direction
rule : {(randInt(7)+1)*10} 1000 {(0,0)>=1 and (0,0)<9}

%Enter Cell
rule : 1 1000 {(0,0)=0 and (1,0)=10}
rule : 2 1000 {(0,0)=0 and (1,-1)=20 and (1,0)!=10}
rule : 3 1000 {(0,0)=0 and (0,-1)=30 and ((1,0)!=10 or (1,-1)!=20)}
rule : 4 1000 {(0,0)=0 and (-1,-1)=40 and ((1,0)!=10 or (1,-1)!=20 or (0,-1)!=30)}
rule : 5 1000 {(0,0)=0 and (-1,0)=50 and ((1,0)!=10 or (1,-1)!=20 or (0,-1)!=30 or (-1,-1)!=40)}
rule : 6 1000 {(0,0)=0 and (-1,1)=60 and ((1,0)!=10 or (1,-1)!=20 or (0,-1)!=30 or (-1,-1)!=40 or (-1,0)!=50)}
rule : 7 1000 {(0,0)=0 and (0,1)=70 and ((1,0)!=10 or (1,-1)!=20 or (0,-1)!=30 or (-1,-1)!=40 or (-1,0)!=50 or (-1,1)!=60)}
rule : 8 1000 {(0,0)=0 and (1,1)=80 and ((1,0)!=10 or (1,-1)!=20 or (0,-1)!=30 or (-1,-1)!=40 or (-1,0)!=50 or (-1,1)!=60 or (0,1)!=70)}

%Leave Cell
rule : 0 1000 {((0,0)=10 and (-1,0)=0)}
rule : 0 1000 {((0,0)=20 and (-1,1)=0 and (0,1)!=10)}
rule : 0 1000 {((0,0)=30 and (0,1)=0 and (1,1)!=10 and (1,0)!=20)}
rule : 0 1000 {((0,0)=40 and (1,1)=0 and (2,1)!=10 and (2,0)!=20 and (1,0)!=30)}
rule : 0 1000 {((0,0)=50 and (1,0)=0 and (2,0)!=10 and (2,-1)!=20 and (1,-1)!=30 and (0,-1)!=40)}
rule : 0 1000 {((0,0)=60 and (1,-1)=0 and (2,-1)!=10 and (2,-2)!=20 and (1,-2)!=30 and (0,-2)!=40 and (0,-1)!=50)}
rule : 0 1000 {((0,0)=70 and (0,-1)=0 and (1,-1)!=10 and (1,-2)!=20 and (0,-2)!=30 and (-1,-2)!=40 and (-1,-1)!=50 and (-1,0)!=60)}
rule : 0 1000 {((0,0)=80 and (-1,-1)=0 and (0,-1)!=10 and (0,-2)!=20 and (-1,-2)!=30 and (-2,-2)!=40 and (-2,-1)!=50 and (-2,0)!=60 and (-1,0)!=70)}

%Try Again
rule : 1 1000 {((0,0)=10 and (-1,0)!=0)}
rule : 1 1000 {((0,0)=20 and (-1,1)!=0)}
rule : 1 1000 {((0,0)=30 and (0,1)!=0)}
rule : 1 1000 {((0,0)=40 and (1,1)!=0)}
rule : 1 1000 {((0,0)=50 and (1,0)!=0)}
rule : 1 1000 {((0,0)=60 and (1,-1)!=0)}
rule : 1 1000 {((0,0)=70 and (0,-1)!=0)}
rule : 1 1000 {((0,0)=80 and (-1,-1)!=0)}

rule : {(0,0)} 1000 { t }

[standard-rule]

rule : {(0,0)} 1000 { t }