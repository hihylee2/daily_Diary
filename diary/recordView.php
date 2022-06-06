<!DOCTYPE html>
<html style="font-size: 16px;">
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
    <meta name="keywords" content="Welcome">
    <meta name="description" content="">
    <meta name="page_type" content="np-template-header-footer-from-plugin">
    <title>서버연동실습</title>
    <link rel="stylesheet" href="nicepage.css" media="screen">
<link rel="stylesheet" href="Monitoring.css" media="screen">
    <script class="u-script" type="text/javascript" src="jquery.js" defer=""></script>
    <script class="u-script" type="text/javascript" src="nicepage.js" defer=""></script>
    <meta name="generator" content="Nicepage 4.6.5, nicepage.com">
    <link id="u-theme-google-font" rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:100,100i,300,300i,400,400i,500,500i,700,700i,900,900i|Open+Sans:300,300i,400,400i,500,500i,600,600i,700,700i,800,800i">
    
    
    <script type="application/ld+json">{

}</script>
    <meta name="theme-color" content="#d8dfd0">
    <meta property="og:title" content="Monitoring">
    <meta property="og:description" content="">
    <meta property="og:type" content="website">
  </head>
  <body class="u-body u-xl-mode"><header class="u-clearfix u-header" id="sec-623e"><div class="u-clearfix u-sheet u-sheet-1">

        </nav>
      </div></header>
     
        <br>
        
            <?php

                $conn = mysqli_connect("localhost", "heeyoung", "1234" , "sungkyul");
                $sql = "SELECT * FROM allMembers ORDER BY no ASC";
                $result = mysqli_query($conn, $sql);

                $number = 0;

                echo "<center><table width = '1000' bordercolor='grey' cellpadding='0' cellspacing='0' bordercolor='#000000' style='border-collapse:collapse'><th >순서</th><th>제목</th><th>일기</th>";


                while($row = mysqli_fetch_assoc($result)) {      

                   echo "<tr>
                   <td width='40' align='center'>".$number."</td><td width='40' align='center'>".$row['idInsert']."</td><td width='150' align='center'>".$row['pwInsert']."</td></tr>";

                   $number = $number+1;

                } 

               
                echo "</table></center><br>";
                mysqli_close($conn);
                  
            ?> 

        <br><br>    

        </div>
    </section>
  
  </body>
</html>