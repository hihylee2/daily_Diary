<?php 

    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');
    // mysqli_query($con,'SET NAMES utf8');

    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {

        // 안드로이드 코드의 postParameters 변수에 적어준 이름을 가지고 값을 전달 받는다.

        $id=$_POST['id_s'];
        $pw=$_POST['pw_s']; // 단말 TimeStamp는 POST로 전달받음    
    


        if(empty($id)){
            $errMSG = "Input name";
        }
        else if(empty($pw)){
            $errMSG = "Input code";
        }

        if(!isset($errMSG))
        {
            try{
                $stmt = $con->prepare('INSERT INTO member(id, pw) VALUES(:id, :pw)');
                $stmt->bindParam(':id', $id);
                $stmt->bindParam(':pw', $pw);

                $con = mysqli_connect("localhost","heeyoung","1234","sungkyul");
        $sql = "SELECT * FROM allMembers where pwInsert like '%$pw%'"; 
        $result = mysqli_query($con, $sql);

        echo ('$result');

        // while($row = mysqli_fetch_assoc($result)) {

        //         $verifypw = $row['pwInsert'];
        //         $verifyid = $row['idInsert'];

        //         if (($verifypw==$pw)&&($verifyid==$id))
        //         {
        //         	echo "<script>alert('로그인되었습니다.'); 
        //         }else{ // 비밀번호가 같지 않다면 알림창을 띄우고 전 페이지로 돌아갑니다
		// echo "<script>alert('아이디 혹은 비밀번호를 확인하세요.'); 
		// 		}
        // }

	mysqli_close($mysqli);
                

                if($stmt->execute())
                {
                    $successMSG = "New record addition";
                }
                else
                {
                    $errMSG = "record addition error";
                }

            } catch(PDOException $e) {
                die("Database error: " . $e->getMessage()); 
            }
        }

        

        // $sql = "SELECT * FROM member where pw like '%$pw%'"; 
        // $result = mysqli_query($sql);

        // if (($pw==$pw)&&($id==$id))
        //         {
        //         	echo "<script>alert('로그인되었습니다.'); location.href='index.html';</script>";
        //         }else{ // 비밀번호가 같지 않다면 알림창을 띄우고 전 페이지로 돌아갑니다
		// echo "<script>alert('아이디 혹은 비밀번호를 확인하세요.'); history.back();</script>";
		// 		}
    }
?>

<!-- <html>
   <body>
        <?php 
        // if (isset($errMSG)) echo $errMSG;
        // if (isset($successMSG)) echo $successMSG;
        // ?>
        
        <form action="<?php $_PHP_SELF ?>" method="POST">
            id: <input type = "text" name = "id" /><br>
            Terminal_time: <input type = "text" name = "Terminal_time" /><br>
            Server_time: <input type = "text" name = "Server_time" /><br>
            x: <input type = "text" name = "x" /><br>
            y: <input type = "text" name = "y" /><br>
            bat_level: <input type = "text" name = "bat_level" /><br>
            <input type = "submit" name = "submit" />
        </form> 
   
   </body>
</html> -->