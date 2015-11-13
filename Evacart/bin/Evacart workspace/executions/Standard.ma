%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Standard.ma
% Author: Ryan Lawler
%
% Description: This model describes a standard evacuation procedure
% for people who are familiar with the surroundings and know where
% the exit is. This model uses a persons knowledge of the surroundings
% to determine a direction of motion for each step, and then to move
% in that direction.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

[top]
components : Standard

[Standard]
type : cell
dim : (17, 17, 2)
delay : transport
defaultDelayTime : 1000
border : nowrapped

%Maze plane neighbors
neighbors : Standard(-1,-1,0) Standard(-1,0,0) Standard(-1,1,0)
neighbors : Standard(0,-1,0)  Standard(0,0,0)  Standard(0,1,0)
neighbors : Standard(1,-1,0)  Standard(1,0,0)  Standard(1,1,0)

%Maze plane to access dist plane. Plan to use the neighbors two cells away in order to determine direction
neighbors :                                     Standard(-2,0,1)
neighbors :                   Standard(-1,-1,1) Standard(-1,0,1) Standard(-1,1,1) 	
neighbors : Standard(0,-2,1)  Standard(0,-1,1)  Standard(0,0,1)  Standard(0,1,1)  Standard(0,2,1)
neighbors :                   Standard(1,-1,1)  Standard(1,0,1)  Standard(1,1,1)
neighbors :                                     Standard(2,0,1)  	

%Initialize Floor Map
initialvalue : -1
initialCellsValue : FTH.val

%Distance plane
zone : dist_plane 	{ (0,0,1)..(16,16,1) }

%Maze plane
zone : floor_plane 	{ (0,0,0)..(16,16,0) }

localtransition : standard-rule

[dist_plane]
rule : {(0,0,0)}    1000 { t }

[floor_plane]
%Keep Current Direction
rule : 10 1000 {(0,0,0)=1 and (-1,0,0)=0 and (-1,0,1)<(0,0,1)}
rule : 20 1000 {(0,0,0)=2 and (-1,1,0)=0 and (-1,1,1)<(0,0,1)}
rule : 30 1000 {(0,0,0)=3 and (0,1,0)=0 and (0,1,1)<(0,0,1) }
rule : 40 1000 {(0,0,0)=4 and (1,1,0)=0 and (1,1,1)<(0,0,1) }
rule : 50 1000 {(0,0,0)=5 and (1,0,0)=0 and (1,0,1)<(0,0,1)}
rule : 60 1000 {(0,0,0)=6 and (1,-1,0)=0 and (1,-1,1)<(0,0,1)} 
rule : 70 1000 {(0,0,0)=7 and (0,-1,0)=0 and (0,-1,1)<(0,0,1) }
rule : 80 1000 {(0,0,0)=8 and (-1,-1,0)=0 and (-1,-1,1)<(0,0,1)} 

%Get Direction
rule : 10 1000 {(0,0,0)>=1 and (0,0,0)<9 and (-1,0,0)=0 and (-1,0,1)<(0,0,1)}
rule : 20 1000 {(0,0,0)>=1 and (0,0,0)<9 and (-1,1,0)=0 and (-1,1,1)<(0,0,1) }
rule : 30 1000 {(0,0,0)>=1 and (0,0,0)<9 and (0,1,0)=0 and (0,1,1)<(0,0,1) }
rule : 40 1000 {(0,0,0)>=1 and (0,0,0)<9 and (1,1,0)=0 and (1,1,1)<(0,0,1) }
rule : 50 1000 {(0,0,0)>=1 and (0,0,0)<9 and (1,0,0)=0 and (1,0,1)<(0,0,1)}
rule : 60 1000 {(0,0,0)>=1 and (0,0,0)<9 and (1,-1,0)=0 and (1,-1,1)<(0,0,1)} 
rule : 70 1000 {(0,0,0)>=1 and (0,0,0)<9 and (0,-1,0)=0 and (0,-1,1)<(0,0,1) }
rule : 80 1000 {(0,0,0)>=1 and (0,0,0)<9 and (-1,-1,0)=0 and (-1,-1,1)<(0,0,1)} 

%Enter Cell
rule : 1 1000 {(0,0,0)=0 and (1,0,0)=10 and (1,0,1)>(0,0,1)}
rule : 2 1000 {(0,0,0)=0 and (1,-1,0)=20 and (1,-1,1)>(0,0,1)}
rule : 3 1000 {(0,0,0)=0 and (0,-1,0)=30 and (0,-1,1)>(0,0,1)}
rule : 4 1000 {(0,0,0)=0 and (-1,-1,0)=40 and (-1,-1,1)>(0,0,1)}
rule : 5 1000 {(0,0,0)=0 and (-1,0,0)=50 and (-1,0,1)>(0,0,1)}
rule : 6 1000 {(0,0,0)=0 and (-1,1,0)=60 and (-1,1,1)>(0,0,1)}
rule : 7 1000 {(0,0,0)=0 and (0,1,0)=70 and (0,1,1)>(0,0,1)}
rule : 8 1000 {(0,0,0)=0 and (1,1,0)=80 and (1,1,1)>(0,0,1)}

%Leave Cell
rule : 0 1000 {((0,0,0)=10 and (-1,0,0)=0 and (-1,0,1)<(0,0,1)) or ((0,0,0)=1 and (-1,0,0)=9)}
rule : 0 1000 {(0,0,0)=20 and (-1,1,0)=0 and (-1,1,1)<(0,0,1) or ((0,0,0)=2 and (-1,1,0)=9)}
rule : 0 1000 {(0,0,0)=30 and (0,1,0)=0 and (0,1,1)<(0,0,1) or ((0,0,0)=3 and (0,1,0)=9)}
rule : 0 1000 {(0,0,0)=40 and (1,1,0)=0 and (1,1,1)<(0,0,1) or ((0,0,0)=4 and (1,1,0)=9)}
rule : 0 1000 {(0,0,0)=50 and (1,0,0)=0 and (1,0,1)<(0,0,1) or ((0,0,0)=5 and (1,0,0)=9)}
rule : 0 1000 {(0,0,0)=60 and (1,-1,0)=0 and (1,-1,1)<(0,0,1) or ((0,0,0)=6 and (1,-1,0)=9)}
rule : 0 1000 {(0,0,0)=70 and (0,-1,0)=0 and (0,-1,1)<(0,0,1) or ((0,0,0)=7 and (0,-1,0)=9)}
rule : 0 1000 {(0,0,0)=80 and (-1,-1,0)=0 and (-1,-1,1)<(0,0,1) or ((0,0,0)=8 and (-1,-1,0)=9)}

rule : {(0,0,0)} 1000 { t }

[standard-rule]

rule : {(0,0,0)} 1000 { t }
