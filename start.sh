sudo docker rm -f hadoop-master 2> /dev/null
sudo docker run -itd \
                --net=hadoop \
                -p 50070:50070 \
                -p 8088:8088 \
                --name hadoop-master \
                --hostname hadoop-master \
                yzj/hadoop


sudo docker rm -f hadoop-slave1 2> /dev/null
sudo docker rm -f hadoop-slave2 2> /dev/null
sudo docker run -itd \
                --net=hadoop \
                --name hadoop-slave1 \
                --hostname hadoop-slave1 \
                yzj/hadoop
sudo docker run -itd \
                --net=hadoop \
                --name hadoop-slave2 \
                --hostname hadoop-slave2 \
                yzj/hadoop

sudo docker exec -it hadoop-master bash
