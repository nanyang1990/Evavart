%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Directional.ma
% Author: Ryan Lawler
%
% Description: This model describes egress when evacuees know the 
% direction to get to the exit.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

[top]
components : Directional

[Directional]
type : cell
dim : (17, 17, 2)
delay : transport
defaultDelayTime : 1000
border : nowrapped

%Maze plane neighbors
neighbors : Directional(-1,-1,0) Directional(-1,0,0) Directional(-1,1,0)
neighbors : Directional(0,-1,0)  Directional(0,0,0)  Directional(0,1,0)
neighbors : Directional(1,-1,0)  Directional(1,0,0)  Directional(1,1,0)

%Maze plane to access dist plane. Plan to use the neighbors two cells away in order to determine direction
neighbors :                                           Directional(-2,0,1)
neighbors :                      Directional(-1,-1,1) Directional(-1,0,1) Directional(-1,1,1) 	
neighbors : Directional(0,-2,1)  Directional(0,-1,1)  Directional(0,0,1)  Directional(0,1,1)  Directional(0,2,1)
neighbors :                      Directional(1,-1,1)  Directional(1,0,1)  Directional(1,1,1)
neighbors :                                           Directional(2,0,1)  	

%Initialize Floor Map
initialvalue : -1
initialCellsValue : Directional.val

%Distance plane
zone : dist_plane 	{ (0,0,1)..(16,16,1) }

%Maze plane
zone : floor_plane 	{ (0,0,0)..(16,16,0) }

localtransition : directional-rule

[dist_plane]
rule : {(0,0,0)}    1000 { t }

[floor_plane]
%Get Direction
rule : 10 1000 {(0,0,0)>=1 and (0,0,0)<9 and (0,0,1)=1}
rule : 20 1000 {(0,0,0)>=1 and (0,0,0)<9 and (0,0,1)=2}
rule : 30 1000 {(0,0,0)>=1 and (0,0,0)<9 and (0,0,1)=3}
rule : 40 1000 {(0,0,0)>=1 and (0,0,0)<9 and (0,0,1)=4}
rule : 50 1000 {(0,0,0)>=1 and (0,0,0)<9 and (0,0,1)=5}
rule : 60 1000 {(0,0,0)>=1 and (0,0,0)<9 and (0,0,1)=6} 
rule : 70 1000 {(0,0,0)>=1 and (0,0,0)<9 and (0,0,1)=7}
rule : 80 1000 {(0,0,0)>=1 and (0,0,0)<9 and (0,0,1)=8} 

%Enter Cell
rule : 1 1000 {(0,0,0)=0 and (1,0,0)=10}
rule : 2 1000 {(0,0,0)=0 and (1,-1,0)=20}
rule : 3 1000 {(0,0,0)=0 and (0,-1,0)=30}
rule : 4 1000 {(0,0,0)=0 and (-1,-1,0)=40}
rule : 5 1000 {(0,0,0)=0 and (-1,0,0)=50}
rule : 6 1000 {(0,0,0)=0 and (-1,1,0)=60}
rule : 7 1000 {(0,0,0)=0 and (0,1,0)=70}
rule : 8 1000 {(0,0,0)=0 and (1,1,0)=80}

%Leave Cell
rule : 0 1000 {((0,0,0)=10 and (-1,0,0)=0) or ((0,0,0)=10 and (-1,0,0)=9)}
rule : 0 1000 {((0,0,0)=20 and (-1,1,0)=0) or ((0,0,0)=20 and (-1,1,0)=9)}
rule : 0 1000 {((0,0,0)=30 and (0,1,0)=0) or ((0,0,0)=30 and (0,1,0)=9)}
rule : 0 1000 {((0,0,0)=40 and (1,1,0)=0) or ((0,0,0)=40 and (1,1,0)=9)}
rule : 0 1000 {((0,0,0)=50 and (1,0,0)=0) or ((0,0,0)=50 and (1,0,0)=9)}
rule : 0 1000 {((0,0,0)=60 and (1,-1,0)=0) or ((0,0,0)=60 and (1,-1,0)=9)}
rule : 0 1000 {((0,0,0)=70 and (0,-1,0)=0) or ((0,0,0)=70 and (0,-1,0)=9)}
rule : 0 1000 {((0,0,0)=80 and (-1,-1,0)=0) or ((0,0,0)=80 and (-1,-1,0)=9)}

rule : {(0,0,0)} 1000 { t }

[directional-rule]

rule : {(0,0,0)} 1000 { t }