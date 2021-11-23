#!/bin/bash

cd /home/vadim/Labs/prog/TCP_chat

rep="src"
jvrep="bin"
compile="javac -d ./$jvrep $rep/client/*.java"
launch="java -cp $jvrep $rep.client.Client $1 $2"

echo ""
echo $compile
$compile
echo ""
echo $launch
echo ""
$launch