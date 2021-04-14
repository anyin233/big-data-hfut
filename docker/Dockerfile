FROM ubuntu:14.04
WORKDIR /root

RUN cp /etc/apt/sources.list /etc/apt/sources.list.bak
RUN echo "deb https://mirrors.ustc.edu.cn/ubuntu/ trusty main restricted universe multiverse\n" \
"deb https://mirrors.ustc.edu.cn/ubuntu/ trusty-security main restricted universe multiverse\n" \
"deb https://mirrors.ustc.edu.cn/ubuntu/ trusty-updates main restricted universe multiverse\n" \
"deb https://mirrors.ustc.edu.cn/ubuntu/ trusty-backports main restricted universe multiverse\n" \
> /etc/apt/sources.list

RUN apt-get update && apt-get install -y openssh-server openjdk-7-jdk wget

COPY hadoop-2.10.1.tar.gz .
RUN tar -xzvf hadoop-2.10.1.tar.gz && \
    mv hadoop-2.10.1 /usr/local/hadoop && \
    rm hadoop-2.10.1.tar.gz

ENV JAVA_HOME=/usr/lib/jvm/java-7-openjdk-amd64
ENV HADOOP_HOME=/usr/local/hadoop
ENV PATH=$PATH:/usr/local/hadoop/bin:/usr/local/hadoop/sbin

RUN mkdir -p ~/.ssh
RUN ssh-keygen -t rsa -f ~/.ssh/id_rsa -P '' && \
    cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
RUN echo "StrictHostKeyChecking no\n" >> .ssh/config

COPY config/* /tmp/
RUN mv /tmp/hadoop-env.sh $HADOOP_HOME/etc/hadoop/hadoop-env.sh && \
    mv /tmp/hdfs-site.xml $HADOOP_HOME/etc/hadoop/hdfs-site.xml && \
    mv /tmp/core-site.xml $HADOOP_HOME/etc/hadoop/core-site.xml && \
    mv /tmp/mapred-site.xml $HADOOP_HOME/etc/hadoop/mapred-site.xml && \
    mv /tmp/yarn-site.xml $HADOOP_HOME/etc/hadoop/yarn-site.xml && \
    mv /tmp/slaves $HADOOP_HOME/etc/hadoop/slaves && \
    mv /tmp/start-hadoop.sh ~/start-hadoop.sh

RUN chmod +x ~/start-hadoop.sh && \
    chmod +x $HADOOP_HOME/sbin/start-dfs.sh && \
    chmod +x $HADOOP_HOME/sbin/start-yarn.sh

RUN mkdir -p ~/hdfs/namenode && \
    mkdir -p ~/hdfs/datanode && \
    mkdir $HADOOP_HOME/logs

RUN $HADOOP_HOME/bin/hdfs namenode -format

CMD [ "sh", "-c", "service ssh start; bash"]
