package com.mycompany.workout;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HdfsRead {
	public HdfsRead() throws IOException {
		Configuration conf = new Configuration();

		conf.addResource(new Path(
				"/project/ProjectM/Proj/Projects/JP/Hadoop/hadoop-1.2.1/conf/core-site.xml"));
		FileSystem fs = FileSystem.get(conf);
		Path pt = new Path("hdfs://localhost:8020/new");
		fs.mkdirs(pt);
	}
	public static void main(String[] args) throws IOException {
		new HdfsRead();
	}
}
