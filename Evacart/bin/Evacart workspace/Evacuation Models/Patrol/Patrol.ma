%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Patrol.ma
% Author: Ryan Lawler
%
% Description: This model describes a patrol evacuation procedure
% where an authority figure follows a patrol route to ensure that all
% personnel have been evacuated.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

[top]
components : Patrol

[Patrol]
type : cell
dim : (17, 17, 2)
delay : transport
defaultDelayTime : 100
border : nowrapped

%Floor plane neighbors
neighbors : Patrol(-1,-1,0) Patrol(-1,0,0) Patrol(-1,1,0)
neighbors : Patrol(0,-1,0)  Patrol(0,0,0)  Patrol(0,1,0)
neighbors : Patrol(1,-1,0)  Patrol(1,0,0)  Patrol(1,1,0)

%Floor plane to access dist plane. 
neighbors : Patrol(-1,-1,1) Patrol(-1,0,1) Patrol(-1,1,1) 	
neighbors : Patrol(0,-1,1)  Patrol(0,0,1)  Patrol(0,1,1)
neighbors : Patrol(1,-1,1)  Patrol(1,0,1)  Patrol(1,1,1)

%Dist plane to access floor plane. 
neighbors : Patrol(-1,-1,-1) Patrol(-1,0,-1) Patrol(-1,1,-1) 	
neighbors : Patrol(0,-1,-1)  Patrol(0,0,-1)  Patrol(0,1,-1)
neighbors : Patrol(1,-1,-1)  Patrol(1,0,-1)  Patrol(1,1,-1)  	

%Initialize Floor Map
initialvalue : 0
initialCellsValue : Patrol.val

%Distance plane
zone : dist_plane 	{ (0,0,1)..(16,16,1) }

%Maze plane
zone : floor_plane 	{ (0,0,0)..(16,16,0) }

localtransition : patrol-rule

[dist_plane]
rule : 23 100 {(0,0,-1)>=10 and (0,0,-1)<90 and (0,0,0)=11}
rule : 22 100 {(0,0,-1)>=10 and (0,0,-1)<90 and (0,0,0)=12}
rule : 21 100 {(0,0,-1)>=10 and (0,0,-1)<90 and (0,0,0)=13}
rule : 37 100 {(0,0,-1)>=10 and (0,0,-1)<90 and (0,0,0)=25}
rule : 36 100 {(0,0,-1)>=10 and (0,0,-1)<90 and (0,0,0)=26}
rule : 35 100 {(0,0,-1)>=10 and (0,0,-1)<90 and (0,0,0)=27}
rule : 67 100 {(0,0,-1)>=10 and (0,0,-1)<90 and (0,0,0)=55}
rule : 66 100 {(0,0,-1)>=10 and (0,0,-1)<90 and (0,0,0)=56}
rule : 65 100 {(0,0,-1)>=10 and (0,0,-1)<90 and (0,0,0)=57}
rule : 81 100 {(0,0,-1)>=10 and (0,0,-1)<90 and (0,0,0)=69}
rule : 80 100 {(0,0,-1)>=10 and (0,0,-1)<90 and (0,0,0)=70}
rule : 79 100 {(0,0,-1)>=10 and (0,0,-1)<90 and (0,0,0)=71}
rule : {(0,0,0)}    100 { t }

[floor_plane]
%Get Direction
rule : 10 100 {(0,0,0)>=1 and (0,0,0)<9 and ((-1,0,1)=((0,0,1)+1))}
rule : 20 100 {(0,0,0)>=1 and (0,0,0)<9 and ((-1,1,1)=((0,0,1)+1))}
rule : 30 100 {(0,0,0)>=1 and (0,0,0)<9 and ((0,1,1)=((0,0,1)+1))}
rule : 40 100 {(0,0,0)>=1 and (0,0,0)<9 and ((1,1,1)=((0,0,1)+1))}
rule : 50 100 {(0,0,0)>=1 and (0,0,0)<9 and ((1,0,1)=((0,0,1)+1))}
rule : 60 100 {(0,0,0)>=1 and (0,0,0)<9 and ((1,-1,1)=((0,0,1)+1))} 
rule : 70 100 {(0,0,0)>=1 and (0,0,0)<9 and ((0,-1,1)=((0,0,1)+1))}
rule : 80 100 {(0,0,0)>=1 and (0,0,0)<9 and ((-1,-1,1)=((0,0,1)+1))} 

%Enter Cell
rule : 1 100 {(0,0,0)=0 and (1,0,0)=10}
rule : 2 100 {(0,0,0)=0 and (1,-1,0)=20}
rule : 3 100 {(0,0,0)=0 and (0,-1,0)=30}
rule : 4 100 {(0,0,0)=0 and (-1,-1,0)=40}
rule : 5 100 {(0,0,0)=0 and (-1,0,0)=50}
rule : 6 100 {(0,0,0)=0 and (-1,1,0)=60}
rule : 7 100 {(0,0,0)=0 and (0,1,0)=70}
rule : 8 100 {(0,0,0)=0 and (1,1,0)=80}

%Leave Cell
rule : 0 100 {(0,0,0)=10 and (-1,0,0)=0}
rule : 0 100 {(0,0,0)=20 and (-1,1,0)=0}
rule : 0 100 {(0,0,0)=30 and (0,1,0)=0}
rule : 0 100 {(0,0,0)=40 and (1,1,0)=0}
rule : 0 100 {(0,0,0)=50 and (1,0,0)=0}
rule : 0 100 {(0,0,0)=60 and (1,-1,0)=0}
rule : 0 100 {(0,0,0)=70 and (0,-1,0)=0}
rule : 0 100 {(0,0,0)=80 and (-1,-1,0)=0}

rule : {(0,0,0)} 100 { t }

[patrol-rule]

rule : {(0,0,0)} 100 { t }
