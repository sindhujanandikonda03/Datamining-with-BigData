<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Data Mining with Big Data</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/plugins/metisMenu/metisMenu.min.css" rel="stylesheet">
    <link href="css/plugins/timeline.css" rel="stylesheet">
    <link href="css/sb-admin-2.css" rel="stylesheet">
    <link href="css/plugins/morris.css" rel="stylesheet">
    <link href="css/home.css" rel="stylesheet">
    <link href="font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
    <script>
  $(document).ready(function(){
	  var formInput=$(this).serialize();
	  $.getJSON('FileListAction.action', formInput,function(data) {
		  console.log(data.fileList);
		  $.each(data.fileList, function(key, val) {
	            // Whatever you what to do, eg.
	            var row=' <tr > <td style="text-align:center;"> '+ val.fileName +'</td></tr>';
	            $('#result').append(row);
	        });
	  });
  });
 </script>  
</head>
<body>
<form action="FlumeSaveAction.action" id="searchForm">
    <div id="wrapper">
        <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
            </div>
            <ul class="nav navbar-top-links navbar-right">
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-user fa-fw"></i>  <i class="fa fa-caret-down"></i>
                    </a>
                </li>
            </ul>
            <div class="navbar-default sidebar" role="navigation">
                <div class="sidebar-nav navbar-collapse">
                    <ul class="nav" id="side-menu">
                        <li>
                            <a class="active" href="home.jsp"><i class=""></i>Home</a>
                        </li>
                         <!--  <li>
                            <a class="active" href="flume.jsp"><i class=""></i>Flume</a>
                        </li>
                         <li>
                            <a class="active" href="hdfs.jsp"><i class=""></i>Hive</a>
                        </li> -->
                        <li>
                            <a class="active" href="clustering.jsp"><i class=""></i>Reports</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Flume</h1>
                    
                </div>
                
            </div>
            <div class="row">
            <br>
            </div>
            <div class="row">
                <div class="col-lg-8s">
                <div class="panel panel-default">
                <div class="panel-heading">
                <i class=""></i> Flume File List
                <table id="result" cellspacing="20" width="700" style="border:1px solid rgb(177, 210, 228);overflow:scroll; " >
  <tbody>
  <tr>
    <th bgcolor="#63E14F" align="left" style="text-align: center;">File List</th>
  </tr>
  </tbody>
</table>

                </div>
                </div>
                </div>
                <div class="col-lg-4">
                </div>
            </div>
            <div style="text-align: center;">
            <input type="submit" value="move to flume"> 
            </div>
        </div>
    </div>
    </form>
</body>

</html>