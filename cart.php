<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <title>cart</title>
  </head>
  <body>
    <div class="container">
    	<div class="row mt-3" >
    		<div class="col bg-primary"> cart </div>
    	</div>
    	<div class="row mt-3">
    		<div class="col-2">
    			       <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">
            Checkout
        </button>
    		</div>
    		<div class="col-8">
    		</div>
    		<div class="col-2">
  				<a href="index.php" class="btn btn-secondary btn-lg" tabindex="-1" role="button" aria-disabled="true">Back</a>
    		</div>

    	</div>
    </div>

    <div class="container">
    	<div class="row mt-3">
    		<div class="col col-lg-11 bg-success overflow-auto">
    			<div class="list-group mt-1">
  					<li class="list-group-item d-flex justify-content-between">
  						<p class="p-0 m-0 flex-grow-1">First item</p> 
                   		<button class="btn-success">EDIT</button>
                   		<button class="btn-danger">DELETE</button>
                	</li>
                	<li class="list-group-item d-flex justify-content-between">
  						<p class="p-0 m-0 flex-grow-1">2</p> 
                   		<button class="btn-success">EDIT</button>
                   		<button class="btn-danger">DELETE</button>
                	</li>
                	<li class="list-group-item d-flex justify-content-between">
  						<p class="p-0 m-0 flex-grow-1">3</p> 
                   		<button class="btn-success">EDIT</button>
                   		<button class="btn-danger">DELETE</button>
                	</li>
                	<li class="list-group-item d-flex justify-content-between">
  						<p class="p-0 m-0 flex-grow-1">4</p> 
                   		<button class="btn-success">EDIT</button>
                   		<button class="btn-danger">DELETE</button>
                	</li>
				  </div>
			   </div>
    	</div>
    </div>

      <!-- Modal -->
  <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">checkout</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <form>
            <div class="form-group">
              <label for="exampleInputEmail1">Email address</label>
              <input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter email">
              <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
            </div>
            <div class="form-group">
              <label for="exampleInputPassword1">Full Name</label>
              <input type="Full name" class="form-control" id="exampleInputPassword1" placeholder="Full name">
            </div>
            <div class="form-group">
              <label for="exampleInputPassword1">Address</label>
              <input type="Full name" class="form-control" id="exampleInputPassword1" placeholder="Address">
            </div>
          </form>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-dismiss="modal">cancel</button>
          <a href="#" class="btn btn-secondary btn-lg" tabindex="-1" role="button" aria-disabled="true">complete</a>
        </div>
      </div>
    </div>
  </div>

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
  </body>
</html>