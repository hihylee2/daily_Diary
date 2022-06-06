<?php 

    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');

    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {

        // 안드로이드 코드의 postParameters 변수에 적어준 이름을 가지고 값을 전달 받는다.

        $idInsert=$_POST['idInsert_s'];
        $pwInsert=$_POST['pwInsert_s']; // 단말 TimeStamp는 POST로 전달받음    
        $pwVerify=$_POST['pwVerify_s'];


        if(empty($idInsert)){
            $errMSG = "Input name";
        }
        else if(empty($pwInsert)){
            $errMSG = "Input code";
        }
        else if (empty($pwVerify)){
            $errMSG = "Input code";
        }

        if(!isset($errMSG))
        {
            try{
                $stmt = $con->prepare('INSERT INTO allMembers(idInsert, pwInsert,pwVerify) VALUES(:idInsert, :pwInsert, :pwVerify)');
                $stmt->bindParam(':idInsert', $idInsert);
                $stmt->bindParam(':pwInsert', $pwInsert);
                $stmt->bindParam(':pwVerify', $pwVerify);
                

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

        
    }
?>

<!-- <html>
   <body>
        <?php 
        if (isset($errMSG)) echo $errMSG;
        if (isset($successMSG)) echo $successMSG;
        ?>
        
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