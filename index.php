DOES THIS WORK AUTOMATICALLY???

<!doctype html>
<html lang="en">
  <head>
	<style>
	body {
	  font-family: "Lato", sans-serif;
	}

	.sidepanel  {
	  width: 0;
	  position: fixed;
	  z-index: 1;
	  height: 350px;
	  top: 50;
	  left: 30;
	  background-color: #111;
	  overflow-x: hidden;
	  transition: 0.5s;
	  padding-top: 60px;
	}

	.sidepanel a {
	  padding: 8px 8px 8px 32px;
	  text-decoration: none;
	  font-size: 25px;
	  color: #818181;
	  display: block;
	  transition: 0.3s;
	}

	.sidepanel a:hover {
	  color: #f1f1f1;
	}

	.sidepanel .closebtn {
	  position: absolute;
	  top: 0;
	  right: 25px;
	  font-size: 36px;
	}

	.openbtn {
	  font-size: 20px;
	  cursor: pointer;
	  background-color: #111;
	  color: white;
	  padding: 10px 15px;
	  border: none;
	}

	.openbtn:hover {
	  background-color:#444;
	}
	</style>  	

    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <title>mainpage</title>
  </head>
  <body>
    <div class="container">
    	<div class="row mt-3" >
    		<div class="col bg-primary"> company art </div>
    	</div>
    	<div class="row mt-3">
    		<div class="col-10 bg-secondary">company info</div>
    		<div class="col-2">
    			<a href="cart.php" class="btn btn-secondary btn-lg" tabindex="-1" role="button" aria-disabled="true">Cart</a>
    		</div>
    	</div>
    	<div class="row mt-1">
    		<div class="col-9">
	    		<div class="input-group mb-3">
	  				<input type="text" class="form-control" placeholder="search for" aria-label="Recipient's username" aria-describedby="button-addon2">
	  				<div class="input-group-append">
	    				<button class="btn btn-outline-secondary" type="button" id="button-addon2">Search</button>
	  				</div>
				</div>
			</div>
    		<div class="col-3">
    			<!-- Button trigger modal -->
				<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">
					  Manufacturer Login
				</button>
    		</div>
    	</div>
    </div>

    <div class="container">
    	<div class="row mt-3">
    		<div class="col col-lg-auto">
				<div id="mySidepanel" class="sidepanel">
				  <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">×</a>
				  <a href="#">About</a>
				  <a href="#">Services</a>
				  <a href="#">Clients</a>
				  <a href="#">Contact</a>
				</div>
				<button class="openbtn" onclick="openNav()">☰</button>		
    		</div>
    		<div class="col col-lg-11 bg-success">
    			
    			<div class="list-group">
  					<button type="button" class="list-group-item list-group-item-action mt-1">Dapibus ac facilisis in</button>
				  	<button type="button" class="list-group-item list-group-item-action">Dapibus ac facilisis in</button>
				  	<button type="button" class="list-group-item list-group-item-action">Dapibus ac facilisis in</button>
				  	<button type="button" class="list-group-item list-group-item-action">Dapibus ac facilisis in</button>
				  	<button type="button" class="list-group-item list-group-item-action">Dapibus ac facilisis in</button>
				</div>

    		    <nav aria-label="Page navigation example">
 					<ul class="pagination justify-content-center mt-1">
    					<li class="page-item"><a class="page-link" href="#">Previous</a></li>
    					<li class="page-item"><a class="page-link" href="#">1</a></li>
			    		<li class="page-item"><a class="page-link" href="#">2</a></li>
			    		<li class="page-item"><a class="page-link" href="#">3</a></li>
			    		<li class="page-item"><a class="page-link" href="#">Next</a></li>
  					</ul>
				</nav>
			</div>
    	</div>
    </div>

	<!-- Modal -->
	<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">Manufacturer login</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	        <form>
  <div class="form-group">
    <label for="exampleInputEmail1">Email address</label>
    <input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter email">
    <small id="emailHelp" class="form-text text-muted">Well never share your email with anyone else.</small>
  </div>
  <div class="form-group">
    <label for="exampleInputPassword1">Password</label>
    <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password">
  </div>
</form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
	        <a href="Manufact.php" class="btn btn-secondary btn-lg" tabindex="-1" role="button" aria-disabled="true">Login</a>
	      </div>
	    </div>
	  </div>
	</div>

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    <script>
function openNav() {
  document.getElementById("mySidepanel").style.width = "250px";
}

function closeNav() {
  document.getElementById("mySidepanel").style.width = "0";
}
</script>

  </body>
</html>