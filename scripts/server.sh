#!/bin/bash

cd /home/vadim/Labs/prog/TCP_chat

rep="src"
jvrep="bin"
compile="javac -d ./$jvrep $rep/server/*.java $rep/utils/*.java $rep/exe/*.java"
launch="java -cp $jvrep $rep.exe.ServerExe $1"

echo ""
echo $compile
$compile
echo ""
echo $launch
echo ""
$launch