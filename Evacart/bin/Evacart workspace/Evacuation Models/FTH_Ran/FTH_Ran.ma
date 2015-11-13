%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% FTH.ma
% Author: Ryan Lawler
%
% Description: This model describes a follow the herd evacuation procedure
% for a mix of people who are familiar or unfamiliar with the surroundings. 
% Those unfamiliar with the surroundings will fall in line and follow 
% the people surrounding them.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

[top]
components : FTH

[FTH]
type : cell
dim : (17, 17, 2)
delay : transport
defaultDelayTime : 1000
border : nowrapped

%Floor plane neighbors
neighbors : FTH(-2,-2,0) FTH(-2,-1,0) FTH(-2,0,0) FTH(-2,1,0) FTH(-2,2,0)
neighbors : FTH(-1,-2,0) FTH(-1,-1,0) FTH(-1,0,0) FTH(-1,1,0) FTH(-1,2,0)
neighbors : FTH(0,-2,0)  FTH(0,-1,0)  FTH(0,0,0)  FTH(0,1,0)  FTH(0,2,0)
neighbors : FTH(1,-2,0)  FTH(1,-1,0)  FTH(1,0,0)  FTH(1,1,0)  FTH(1,2,0)
neighbors : FTH(2,-2,0)  FTH(2,-1,0)  FTH(2,0,0)  FTH(2,1,0)  FTH(2,2,0)

%Distance plane neighbors. Plan to use the neighbors two cells away in order to determine direction
neighbors :                           FTH(-2,0,1)
neighbors :              FTH(-1,-1,1) FTH(-1,0,1) FTH(-1,1,1) 	
neighbors : FTH(0,-2,1)  FTH(0,-1,1)  FTH(0,0,1)  FTH(0,1,1)  FTH(0,2,1)
neighbors :              FTH(1,-1,1)  FTH(1,0,1)  FTH(1,1,1)
neighbors :                           FTH(2,0,1)  	

%Initialize Floor Map
initialvalue : 0
initialCellsValue : FTH.val

%Distance plane
zone : dist_plane 	{ (0,0,1)..(16,16,1) }

%Maze plane
zone : floor_plane 	{ (0,0,0)..(16,16,0) }

localtransition : fth-rule

[dist_plane]
rule : {(0,0,0)}    1000 { t }

[floor_plane]
%Exit Building
rule : 0 1000 {(0,0,0)>0 and (0,0,0)<90 and ((0,1,0)=900 or (1,1,0)=900 or (1,0,0)=900 or (1,-1,0)=900 or (0,-1,0)=900 or (-1,-1,0)=900 or (-1,0,0)=900 or (-1,1,0)=900)}

%Change Stage
rule : 101 1000 {((0,0,0)>=1 and (0,0,0)<9) and (stateCount(201)>0 or stateCount(210)>0)}
rule : 102 1000 {((0,0,0)>=1 and (0,0,0)<9) and (stateCount(202)>0 or stateCount(220)>0)}
rule : 103 1000 {((0,0,0)>=1 and (0,0,0)<9) and (stateCount(203)>0 or stateCount(230)>0)}
rule : 104 1000 {((0,0,0)>=1 and (0,0,0)<9) and (stateCount(204)>0 or stateCount(240)>0)}
rule : 105 1000 {((0,0,0)>=1 and (0,0,0)<9) and (stateCount(205)>0 or stateCount(250)>0)}
rule : 106 1000 {((0,0,0)>=1 and (0,0,0)<9) and (stateCount(206)>0 or stateCount(260)>0)}
rule : 107 1000 {((0,0,0)>=1 and (0,0,0)<9) and (stateCount(207)>0 or stateCount(270)>0)}
rule : 108 1000 {((0,0,0)>=1 and (0,0,0)<9) and (stateCount(208)>0 or stateCount(280)>0)}

%Unknowledgeable Person - Become Follower
rule : 110 1000 {(0,0,0)>=101 and (0,0,0)<109 and (-1,0,0)<=0 and ((-2,0,0)=201 or (0,2,0)=207 or (0,-2,0)=203 or (0,1,0)=207 or (0,-1,0)=203 or (-1,-1,0)=201 or (-1,1,0)=201 or (0,1,0)=270 or (0,-1,0)=230)}
rule : 120 1000 {(0,0,0)>=101 and (0,0,0)<109 and (-1,1,0)<=0 and ((-2,2,0)=202 or (2,2,0)=208 or (-2,-2,0)=204 or (1,1,0)=208 or (-1,-1,0)=204 or (-2,0,0)=202 or (0,2,0)=202 or (1,1,0)=280 or (-1,-1,0)=240)}
rule : 130 1000 {(0,0,0)>=101 and (0,0,0)<109 and (0,1,0)<=0 and ((0,2,0)=203 or (2,0,0)=201 or (-2,0,0)=205 or (1,0,0)=201 or (-1,0,0)=205 or (-1,1,0)=203 or (1,1,0)=203 or (1,0,0)=210 or (-1,0,0)=250)}
rule : 140 1000 {(0,0,0)>=101 and (0,0,0)<109 and (1,1,0)<=0 and ((2,2,0)=204 or (2,-2,0)=202 or (-2,2,0)=206 or (1,-1,0)=202 or (-1,1,0)=206 or (0,2,0)=204 or (2,0,0)=204 or (1,-1,0)=220 or (-1,1,0)=260)}
rule : 150 1000 {(0,0,0)>=101 and (0,0,0)<109 and (1,0,0)<=0 and ((2,0,0)=205 or (0,-2,0)=203 or (0,2,0)=207 or (0,-1,0)=203 or (0,1,0)=207 or (1,1,0)=205 or (1,-1,0)=205 or (0,-1,0)=230 or (0,1,0)=270)}
rule : 160 1000 {(0,0,0)>=101 and (0,0,0)<109 and (1,-1,0)<=0 and ((2,-2,0)=206 or (-2,-2,0)=204 or (2,2,0)=208 or (-1,-1,0)=204 or (1,1,0)=208 or (2,0,0)=206 or (0,-2,0)=206 or (-1,-1,0)=240 or (1,1,0)=280)}
rule : 170 1000 {(0,0,0)>=101 and (0,0,0)<109 and (0,-1,0)<=0 and ((0,-2,0)=207 or (-2,0,0)=205 or (2,0,0)=201 or (-1,0,0)=205 or (1,0,0)=201 or (1,-1,0)=207 or (-1,-1,0)=207 or (-1,0,0)=250 or (1,0,0)=210)}
rule : 180 1000 {(0,0,0)>=101 and (0,0,0)<109 and (-1,-1,0)<=0 and ((-2,-2,0)=208 or (-2,2,0)=206 or (2,-2,0)=202 or (-1,1,0)=206 or (1,-1,0)=202 or (0,-2,0)=208 or (-2,0,0)=208 or (-1,1,0)=260 or (1,-1,0)=220)}

%Get Direction
rule : {(randInt(7)+1)*10} 1000 {(0,0,0)>=1 and (0,0,0)<9}

%Enter Cell
rule : 1 1000 {(0,0,0)=0 and (1,0,0)=10}
rule : 2 1000 {(0,0,0)=0 and (1,-1,0)=20 and ((1,0,0)!=10 or (1,0,0)!=110)}
rule : 3 1000 {(0,0,0)=0 and (0,-1,0)=30 and ((1,0,0)!=10 or (1,-1,0)!=20 or (1,0,0)!=110 or (1,-1,0)!=120)}
rule : 4 1000 {(0,0,0)=0 and (-1,-1,0)=40 and ((1,0,0)!=10 or (1,-1,0)!=20 or (0,-1,0)!=30 or (1,0,0)!=110 or (1,-1,0)!=120 or (0,-1,0)!=130)}
rule : 5 1000 {(0,0,0)=0 and (-1,0,0)=50 and ((1,0,0)!=10 or (1,-1,0)!=20 or (0,-1,0)!=30 or (-1,-1,0)!=40 or (1,0,0)!=110 or (1,-1,0)!=120 or (0,-1,0)!=130 or (-1,-1,0)!=140)}
rule : 6 1000 {(0,0,0)=0 and (-1,1,0)=60 and ((1,0,0)!=10 or (1,-1,0)!=20 or (0,-1,0)!=30 or (-1,-1,0)!=40 or (-1,0,0)!=50 or (1,0,0)!=110 or (1,-1,0)!=120 or (0,-1,0)!=130 or (-1,-1,0)!=140 or (-1,0,0)!=150)}
rule : 7 1000 {(0,0,0)=0 and (0,1,0)=70 and ((1,0,0)!=10 or (1,-1,0)!=20 or (0,-1,0)!=30 or (-1,-1,0)!=40 or (-1,0,0)!=50 or (-1,1,0)!=60 or (1,0,0)!=110 or (1,-1,0)!=120 or (0,-1,0)!=130 or (-1,-1,0)!=140 or (-1,0,0)!=150 or (-1,1,0)!=160)}
rule : 8 1000 {(0,0,0)=0 and (1,1,0)=80 and ((1,0,0)!=10 or (1,-1,0)!=20 or (0,-1,0)!=30 or (-1,-1,0)!=40 or (-1,0,0)!=50 or (-1,1,0)!=60 or (0,1,0)!=70 or (1,0,0)!=110 or (1,-1,0)!=120 or (0,-1,0)!=130 or (-1,-1,0)!=140 or (-1,0,0)!=150 or (-1,1,0)!=160 or (0,1,0)!=170)}

%Leave Cell - Collision detection for Random and Followers
rule : 0 1000 {((0,0,0)=10 and (-1,0,0)=0)}
rule : 0 1000 {((0,0,0)=20 and (-1,1,0)=0 and (0,1,0)!=10 and (0,1,0)!=110)}
rule : 0 1000 {((0,0,0)=30 and (0,1,0)=0 and (1,1,0)!=10 and (1,0,0)!=20 and (1,1,0)!=110 and (1,0,0)!=120)}
rule : 0 1000 {((0,0,0)=40 and (1,1,0)=0 and (2,1,0)!=10 and (2,0,0)!=20 and (1,0,0)!=30 and (2,1,0)!=110 and (2,0,0)!=120 and (1,0,0)!=130)}
rule : 0 1000 {((0,0,0)=50 and (1,0,0)=0 and (2,0,0)!=10 and (2,-1,0)!=20 and (1,-1,0)!=30 and (0,-1,0)!=40 and (2,0,0)!=110 and (2,-1,0)!=120 and (1,-1,0)!=130 and (0,-1,0)!=140)}
rule : 0 1000 {((0,0,0)=60 and (1,-1,0)=0 and (2,-1,0)!=10 and (2,-2,0)!=20 and (1,-2,0)!=30 and (0,-2,0)!=40 and (0,-1,0)!=50 and (2,-1,0)!=110 and (2,-2,0)!=120 and (1,-2,0)!=130 and (0,-2,0)!=140 and (0,-1,0)!=150)}
rule : 0 1000 {((0,0,0)=70 and (0,-1,0)=0 and (1,-1,0)!=10 and (1,-2,0)!=20 and (0,-2,0)!=30 and (-1,-2,0)!=40 and (-1,-1,0)!=50 and (-1,0,0)!=60 and (1,-1,0)!=110 and (1,-2,0)!=120 and (0,-2,0)!=130 and (-1,-2,0)!=140 and (-1,-1,0)!=150 and (-1,0,0)!=160)}
rule : 0 1000 {((0,0,0)=80 and (-1,-1,0)=0 and (0,-1,0)!=10 and (0,-2,0)!=20 and (-1,-2,0)!=30 and (-2,-2,0)!=40 and (-2,-1,0)!=50 and (-2,0,0)!=60 and (-1,0,0)!=70 and (0,-1,0)!=110 and (0,-2,0)!=120 and (-1,-2,0)!=130 and (-2,-2,0)!=140 and (-2,-1,0)!=150 and (-2,0,0)!=160 and (-1,0,0)!=170)}

%Try Again
rule : 1 1000 {((0,0,0)=10 and (-1,0,0)!=0)}
rule : 1 1000 {((0,0,0)=20 and (-1,1,0)!=0)}
rule : 1 1000 {((0,0,0)=30 and (0,1,0)!=0)}
rule : 1 1000 {((0,0,0)=40 and (1,1,0)!=0)}
rule : 1 1000 {((0,0,0)=50 and (1,0,0)!=0)}
rule : 1 1000 {((0,0,0)=60 and (1,-1,0)!=0)}
rule : 1 1000 {((0,0,0)=70 and (0,-1,0)!=0)}
rule : 1 1000 {((0,0,0)=80 and (-1,-1,0)!=0)}


%Leader - Change Direction Exit
rule : 210 1000 {((0,0,0)>=201 and (0,0,0)<209 and (-2,0,0)=900)}
rule : 220 1000 {((0,0,0)>=201 and (0,0,0)<209 and (-2,2,0)=900)}
rule : 230 1000 {((0,0,0)>=201 and (0,0,0)<209 and (0,2,0)=900)}
rule : 240 1000 {((0,0,0)>=201 and (0,0,0)<209 and (2,2,0)=900)}
rule : 250 1000 {((0,0,0)>=201 and (0,0,0)<209 and (2,0,0)=900)}
rule : 260 1000 {((0,0,0)>=201 and (0,0,0)<209 and (2,-2,0)=900)}
rule : 270 1000 {((0,0,0)>=201 and (0,0,0)<209 and (0,-2,0)=900)}
rule : 280 1000 {((0,0,0)>=201 and (0,0,0)<209 and (-2,-2,0)=900)}

%Follower - Change Direction Exit
rule : 110 1000 {(0,0,0)>=101 and (0,0,0)<109 and ((-2,0,0)=900)}
rule : 120 1000 {(0,0,0)>=101 and (0,0,0)<109 and ((-2,2,0)=900)}
rule : 130 1000 {(0,0,0)>=101 and (0,0,0)<109 and ((0,2,0)=900)}
rule : 140 1000 {(0,0,0)>=101 and (0,0,0)<109 and ((2,2,0)=900)}
rule : 150 1000 {(0,0,0)>=101 and (0,0,0)<109 and ((2,0,0)=900)}
rule : 160 1000 {(0,0,0)>=101 and (0,0,0)<109 and ((2,-2,0)=900)}
rule : 170 1000 {(0,0,0)>=101 and (0,0,0)<109 and ((0,-2,0)=900)}
rule : 180 1000 {(0,0,0)>=101 and (0,0,0)<109 and ((-2,-2,0)=900)}

rule : 0 1000 {(0,0,0)>=101 and (0,0,0)<109 and ((-1,0,0)=900 or (-1,1,0)=900 or (0,1,0)=900 or (1,1,0)=900 or (1,0,0)=900 or (1,-1,0)=900 or (0,-1,0)=900 or (-1,-1,0)=900)}

%Leader - Keep Current Direction
rule : 210 1000 {(0,0,0)=201 and (-1,0,0)=0 and (-2,0,0)!=-1}
rule : 220 1000 {(0,0,0)=202 and (-1,1,0)=0 and (-2,2,0)!=-1}
rule : 230 1000 {(0,0,0)=203 and (0,1,0)=0 and (0,2,0)!=-1}
rule : 240 1000 {(0,0,0)=204 and (1,1,0)=0 and (2,2,0)!=-1}
rule : 250 1000 {(0,0,0)=205 and (1,0,0)=0 and (2,0,0)!=-1}
rule : 260 1000 {(0,0,0)=206 and (1,-1,0)=0 and (2,-2,0)!=-1} 
rule : 270 1000 {(0,0,0)=207 and (0,-1,0)=0 and (0,-2,0)!=-1 }
rule : 280 1000 {(0,0,0)=208 and (-1,-1,0)=0 and (-2,-2,0)!=-1} 

%Leader - Change Direction Wall
rule : 201 1000 {((0,0,0)=202 and (-2,2,0)=-1)}
rule : 202 1000 {((0,0,0)=203 and (0,2,0)=-1)}
rule : 203 1000 {((0,0,0)=204 and (2,2,0)=-1)}
rule : 204 1000 {((0,0,0)=205 and (2,0,0)=-1)}
rule : 205 1000 {((0,0,0)=206 and (2,-2,0)=-1)}
rule : 206 1000 {((0,0,0)=207 and (0,-2,0)=-1)} 
rule : 207 1000 {((0,0,0)=208 and (-2,-2,0)=-1)}
rule : 208 1000 {((0,0,0)=201 and (-2,0,0)=-1)} 

%Leader - Enter Cell
rule : 201 1000 {(0,0,0)=0 and (1,0,0)=210}
rule : 202 1000 {(0,0,0)=0 and (1,-1,0)=220}
rule : 203 1000 {(0,0,0)=0 and (0,-1,0)=230}
rule : 204 1000 {(0,0,0)=0 and (-1,-1,0)=240}
rule : 205 1000 {(0,0,0)=0 and (-1,0,0)=250}
rule : 206 1000 {(0,0,0)=0 and (-1,1,0)=260}
rule : 207 1000 {(0,0,0)=0 and (0,1,0)=270}
rule : 208 1000 {(0,0,0)=0 and (1,1,0)=280}

%Leader - Leave Cell
rule : 0 1000 {((0,0,0)=210 and (-1,0,0)=0) or ((0,0,0)=201 and (-1,0,0)=900)}
rule : 0 1000 {((0,0,0)=220 and (-1,1,0)=0) or ((0,0,0)=202 and (-1,1,0)=900)}
rule : 0 1000 {((0,0,0)=230 and (0,1,0)=0) or ((0,0,0)=203 and (0,1,0)=900)}
rule : 0 1000 {((0,0,0)=240 and (1,1,0)=0) or ((0,0,0)=204 and (1,1,0)=900)}
rule : 0 1000 {((0,0,0)=250 and (1,0,0)=0) or ((0,0,0)=205 and (1,0,0)=900)}
rule : 0 1000 {((0,0,0)=260 and (1,-1,0)=0) or ((0,0,0)=206 and (1,-1,0)=900)}
rule : 0 1000 {((0,0,0)=270 and (0,-1,0)=0) or ((0,0,0)=207 and (0,-1,0)=900)}
rule : 0 1000 {((0,0,0)=280 and (-1,-1,0)=0) or ((0,0,0)=208 and (-1,-1,0)=900)}

%Follower - Change Direction Exit
rule : 110 1000 {((0,0,0)>=101 and (0,0,0)<109 and (-2,0,0)=900)}
rule : 120 1000 {((0,0,0)>=101 and (0,0,0)<109 and (-2,2,0)=900)}
rule : 130 1000 {((0,0,0)>=101 and (0,0,0)<109 and (0,2,0)=900)}
rule : 140 1000 {((0,0,0)>=101 and (0,0,0)<109 and (2,2,0)=900)}
rule : 150 1000 {((0,0,0)>=101 and (0,0,0)<109 and (2,0,0)=900)}
rule : 160 1000 {((0,0,0)>=101 and (0,0,0)<109 and (2,-2,0)=900)}
rule : 170 1000 {((0,0,0)>=101 and (0,0,0)<109 and (0,-2,0)=900)}
rule : 180 1000 {((0,0,0)>=101 and (0,0,0)<109 and (-2,-2,0)=900)}

%Follower - Respond to Leader Direction Change
rule : 101 1000 {(0,0,0)=102 and stateCount(201)>0}
rule : 102 1000 {(0,0,0)=103 and stateCount(202)>0}
rule : 103 1000 {(0,0,0)=104 and stateCount(203)>0}
rule : 104 1000 {(0,0,0)=105 and stateCount(204)>0}
rule : 105 1000 {(0,0,0)=106 and stateCount(205)>0}
rule : 106 1000 {(0,0,0)=107 and stateCount(206)>0}
rule : 107 1000 {(0,0,0)=108 and stateCount(207)>0}
rule : 108 1000 {(0,0,0)=101 and stateCount(208)>0}

%Follower - Directly behind Leader
%rule : 110 1000 {((0,0,0)>=101 and (0,0,0)<109) and (-1,0,0)=0 and ((-1,0,0)=208 or (-1,0,0)=202)}
%rule : 120 1000 {((0,0,0)>=101 and (0,0,0)<109) and (-1,0,0)=0 and ((-1,1,0)=201 or (-1,1,0)=203)}
%rule : 130 1000 {((0,0,0)>=101 and (0,0,0)<109) and (-1,0,0)=0 and ((0,1,0)=202 or (0,1,0)=204)}
%rule : 140 1000 {((0,0,0)>=101 and (0,0,0)<109) and (-1,0,0)=0 and ((1,1,0)=203 or (1,1,0)=205)}
%rule : 150 1000 {((0,0,0)>=101 and (0,0,0)<109) and (-1,0,0)=0 and (2,0,0)=0 and ((1,0,0)=204 or (1,0,0)=206)}
%rule : 160 1000 {((0,0,0)>=101 and (0,0,0)<109) and (-1,0,0)=0 and (2,-2,0)=0 and ((1,-1,0)=205 or (1,-1,0)=207)}
%rule : 170 1000 {((0,0,0)>=101 and (0,0,0)<109) and (-1,0,0)=0 and (0,-2,0)=0 and ((0,-1,0)=206 or (0,-1,0)=208)}
%rule : 180 1000 {((0,0,0)>=101 and (0,0,0)<109) and (-1,0,0)=0 and (-2,-2,0)=0 and ((-1,-1,0)=207 or (-1,-1,0)=201)}

%Follower - Get Direction off Leader
rule : 110 1000 {((0,0,0)>=101 and (0,0,0)<109) and ((-1,-1,0)=210 or (-1,0,0)=210 or (-1,1,0)=210 or (-2,-2,0)=210 or (-2,0,0)=210 or (-2,2,0)=210)}
rule : 120 1000 {((0,0,0)>=101 and (0,0,0)<109) and ((-2,0,0)=220 or (-1,-1,0)=220 or (0,2,0)=220 or (-2,1,0)=220 or (-2,2,0)=220 or (-1,2,0)=220)}
rule : 130 1000 {((0,0,0)>=101 and (0,0,0)<109) and ((-1,1,0)=230 or (0,1,0)=230 or (1,1,0)=230 or (-2,2,0)=230 or (0,2,0)=230 or (2,2,0)=230)}
rule : 140 1000 {((0,0,0)>=101 and (0,0,0)<109) and ((0,2,0)=240 or (1,1,0)=240 or (2,0,0)=240 or (1,2,0)=240 or (2,2,0)=240 or (2,1,0)=240)}
rule : 150 1000 {((0,0,0)>=101 and (0,0,0)<109) and ((1,1,0)=250 or (1,0,0)=250 or (1,-1,0)=250 or (2,2,0)=250 or (2,0,0)=250 or (2,-2,0)=250)}
rule : 160 1000 {((0,0,0)>=101 and (0,0,0)<109) and ((2,0,0)=260 or (1,-1,0)=260 or (0,-2,0)=260 or (2,-1,0)=260 or (2,-2,0)=260 or (1,-2,0)=260)}
rule : 170 1000 {((0,0,0)>=101 and (0,0,0)<109) and ((1,-1,0)=270 or (0,-1,0)=270 or (-1,-1,0)=270 or (2,-2,0)=270 or (0,-2,0)=270 or (-2,-2,0)=270)}
rule : 180 1000 {((0,0,0)>=101 and (0,0,0)<109) and ((0,-2,0)=280 or (-1,-1,0)=280 or (-2,0,0)=280 or (-1,-2,0)=280 or (-2,-2,0)=280 or (-2,-1,0)=280)}

%Follower - Get Direction off Follower
rule : 110 1000 {((0,0,0)>=101 and (0,0,0)<109) and (-1,-1,0)=230 and (-1,1,0)=270 and ((-1,-1,0)=110 or (-1,0,0)=110 or (-1,1,0)=110 or (-2,-1,0)=110 or (-2,0,0)=110 or (-2,1,0)=110)}
rule : 120 1000 {((0,0,0)>=101 and (0,0,0)<109) and ((-2,0,0)=120 or (-1,1,0)=120 or (0,2,0)=120 or (-2,1,0)=120 or (-2,2,0)=120 or (-1,2,0)=120)}
rule : 130 1000 {((0,0,0)>=101 and (0,0,0)<109) and (-1,1,0)=250 and (1,1,0)=210 and ((-1,1,0)=130 or (0,1,0)=130 or (1,1,0)=130 or (-1,2,0)=130 or (0,2,0)=130 or (1,2,0)=130)}
rule : 140 1000 {((0,0,0)>=101 and (0,0,0)<109) and ((0,2,0)=140 or (1,1,0)=140 or (2,0,0)=140 or (1,2,0)=140 or (2,2,0)=140 or (2,1,0)=140)}
rule : 150 1000 {((0,0,0)>=101 and (0,0,0)<109) and (1,1,0)=270 and (1,-1,0)=230 and ((1,1,0)=150 or (1,0,0)=150 or (1,-1,0)=150 or (2,1,0)=150 or (2,0,0)=150 or (2,-1,0)=150)}
rule : 160 1000 {((0,0,0)>=101 and (0,0,0)<109) and ((2,0,0)=160 or (1,-1,0)=160 or (0,-2,0)=160 or (2,-1,0)=160 or (2,-2,0)=160 or (1,-2,0)=160)}
rule : 170 1000 {((0,0,0)>=101 and (0,0,0)<109) and (1,-1,0)=210 and (-1,-1,0)=250 and ((1,-1,0)=170 or (0,-1,0)=170 or (-1,-1,0)=170 or (1,-2,0)=170 or (0,-2,0)=170 or (-1,-2,0)=170)}
rule : 180 1000 {((0,0,0)>=101 and (0,0,0)<109) and ((0,-2,0)=180 or (-1,-1,0)=180 or (-2,0,0)=180 or (-1,-2,0)=180 or (-2,-2,0)=180 or (-2,-1,0)=180)}

%Follower - Carry On Direction
rule : 110 1000 {(0,0,0)=101 and (-1,0,0)=0 and (stateCount(201)=0 and stateCount(210)=0 and stateCount(202)=0 and stateCount(220)=0 and stateCount(203)=0 and stateCount(230)=0 and stateCount(204)=0 and stateCount(240)=0 and stateCount(205)=0 and stateCount(250)=0 and stateCount(206)=0 and stateCount(260)=0 and stateCount(207)=0 and stateCount(270)=0 and stateCount(208)=0 and stateCount(280)=0)}
rule : 120 1000 {(0,0,0)=102 and (-1,1,0)=0 and (stateCount(201)=0 and stateCount(210)=0 and stateCount(202)=0 and stateCount(220)=0 and stateCount(203)=0 and stateCount(230)=0 and stateCount(204)=0 and stateCount(240)=0 and stateCount(205)=0 and stateCount(250)=0 and stateCount(206)=0 and stateCount(260)=0 and stateCount(207)=0 and stateCount(270)=0 and stateCount(208)=0 and stateCount(280)=0)}
rule : 130 1000 {(0,0,0)=103 and (0,1,0)=0 and (stateCount(201)=0 and stateCount(210)=0 and stateCount(202)=0 and stateCount(220)=0 and stateCount(203)=0 and stateCount(230)=0 and stateCount(204)=0 and stateCount(240)=0 and stateCount(205)=0 and stateCount(250)=0 and stateCount(206)=0 and stateCount(260)=0 and stateCount(207)=0 and stateCount(270)=0 and stateCount(208)=0 and stateCount(280)=0)}
rule : 140 1000 {(0,0,0)=104 and (1,1,0)=0 and (stateCount(201)=0 and stateCount(210)=0 and stateCount(202)=0 and stateCount(220)=0 and stateCount(203)=0 and stateCount(230)=0 and stateCount(204)=0 and stateCount(240)=0 and stateCount(205)=0 and stateCount(250)=0 and stateCount(206)=0 and stateCount(260)=0 and stateCount(207)=0 and stateCount(270)=0 and stateCount(208)=0 and stateCount(280)=0)}
rule : 150 1000 {(0,0,0)=105 and (1,0,0)=0 and (stateCount(201)=0 and stateCount(210)=0 and stateCount(202)=0 and stateCount(220)=0 and stateCount(203)=0 and stateCount(230)=0 and stateCount(204)=0 and stateCount(240)=0 and stateCount(205)=0 and stateCount(250)=0 and stateCount(206)=0 and stateCount(260)=0 and stateCount(207)=0 and stateCount(270)=0 and stateCount(208)=0 and stateCount(280)=0)}
rule : 160 1000 {(0,0,0)=106 and (1,-1,0)=0 and (stateCount(201)=0 and stateCount(210)=0 and stateCount(202)=0 and stateCount(220)=0 and stateCount(203)=0 and stateCount(230)=0 and stateCount(204)=0 and stateCount(240)=0 and stateCount(205)=0 and stateCount(250)=0 and stateCount(206)=0 and stateCount(260)=0 and stateCount(207)=0 and stateCount(270)=0 and stateCount(208)=0 and stateCount(280)=0)}
rule : 170 1000 {(0,0,0)=107 and (0,-1,0)=0 and (stateCount(201)=0 and stateCount(210)=0 and stateCount(202)=0 and stateCount(220)=0 and stateCount(203)=0 and stateCount(230)=0 and stateCount(204)=0 and stateCount(240)=0 and stateCount(205)=0 and stateCount(250)=0 and stateCount(206)=0 and stateCount(260)=0 and stateCount(207)=0 and stateCount(270)=0 and stateCount(208)=0 and stateCount(280)=0)}
rule : 180 1000 {(0,0,0)=108 and (-1,-1,0)=0 and (stateCount(201)=0 and stateCount(210)=0 and stateCount(202)=0 and stateCount(220)=0 and stateCount(203)=0 and stateCount(230)=0 and stateCount(204)=0 and stateCount(240)=0 and stateCount(205)=0 and stateCount(250)=0 and stateCount(206)=0 and stateCount(260)=0 and stateCount(207)=0 and stateCount(270)=0 and stateCount(208)=0 and stateCount(280)=0)}

rule : 170 1000 {((0,0,0)=108 or (0,0,0)=101 or (0,0,0)=102) and (-1,0,0)=-1 and (stateCount(201)=0 and stateCount(210)=0 and stateCount(202)=0 and stateCount(220)=0 and stateCount(203)=0 and stateCount(230)=0 and stateCount(204)=0 and stateCount(240)=0 and stateCount(205)=0 and stateCount(250)=0 and stateCount(206)=0 and stateCount(260)=0 and stateCount(207)=0 and stateCount(270)=0 and stateCount(208)=0 and stateCount(280)=0)}
rule : 150 1000 {((0,0,0)=106 or (0,0,0)=107 or (0,0,0)=108) and (0,-1,0)=-1 and (stateCount(201)=0 and stateCount(210)=0 and stateCount(202)=0 and stateCount(220)=0 and stateCount(203)=0 and stateCount(230)=0 and stateCount(204)=0 and stateCount(240)=0 and stateCount(205)=0 and stateCount(250)=0 and stateCount(206)=0 and stateCount(260)=0 and stateCount(207)=0 and stateCount(270)=0 and stateCount(208)=0 and stateCount(280)=0)}
rule : 130 1000 {((0,0,0)=104 or (0,0,0)=105 or (0,0,0)=106) and (1,0,0)=-1 and (stateCount(201)=0 and stateCount(210)=0 and stateCount(202)=0 and stateCount(220)=0 and stateCount(203)=0 and stateCount(230)=0 and stateCount(204)=0 and stateCount(240)=0 and stateCount(205)=0 and stateCount(250)=0 and stateCount(206)=0 and stateCount(260)=0 and stateCount(207)=0 and stateCount(270)=0 and stateCount(208)=0 and stateCount(280)=0)}
rule : 110 1000 {((0,0,0)=102 or (0,0,0)=103 or (0,0,0)=104) and (0,1,0)=-1 and (stateCount(201)=0 and stateCount(210)=0 and stateCount(202)=0 and stateCount(220)=0 and stateCount(203)=0 and stateCount(230)=0 and stateCount(204)=0 and stateCount(240)=0 and stateCount(205)=0 and stateCount(250)=0 and stateCount(206)=0 and stateCount(260)=0 and stateCount(207)=0 and stateCount(270)=0 and stateCount(208)=0 and stateCount(280)=0)}

%Follower - Enter Cell
rule : 101 1000 {(0,0,0)=0 and (1,0,0)=110}
rule : 102 1000 {(0,0,0)=0 and (1,-1,0)=120}
rule : 103 1000 {(0,0,0)=0 and (0,-1,0)=130}
rule : 104 1000 {(0,0,0)=0 and (-1,-1,0)=140}
rule : 105 1000 {(0,0,0)=0 and (-1,0,0)=150}
rule : 106 1000 {(0,0,0)=0 and (-1,1,0)=160}
rule : 107 1000 {(0,0,0)=0 and (0,1,0)=170}
rule : 108 1000 {(0,0,0)=0 and (1,1,0)=180}

%Follower - Leave Cell
rule : 0 1000 {((0,0,0)=110 and (-1,0,0)=0) or ((0,0,0)=101 and (-1,0,0)=900)}
rule : 0 1000 {((0,0,0)=120 and (-1,1,0)=0) or ((0,0,0)=102 and (-1,1,0)=900)}
rule : 0 1000 {((0,0,0)=130 and (0,1,0)=0) or ((0,0,0)=103 and (0,1,0)=900)}
rule : 0 1000 {((0,0,0)=140 and (1,1,0)=0) or ((0,0,0)=104 and (1,1,0)=900)}
rule : 0 1000 {((0,0,0)=150 and (1,0,0)=0) or ((0,0,0)=105 and (1,0,0)=900)}
rule : 0 1000 {((0,0,0)=160 and (1,-1,0)=0) or ((0,0,0)=106 and (1,-1,0)=900)}
rule : 0 1000 {((0,0,0)=170 and (0,-1,0)=0) or ((0,0,0)=107 and (0,-1,0)=900)}
rule : 0 1000 {((0,0,0)=180 and (-1,-1,0)=0) or ((0,0,0)=108 and (-1,-1,0)=900)}

rule : {(0,0,0)} 1000 { t }

[fth-rule]

rule : {(0,0,0)} 1000 { t }