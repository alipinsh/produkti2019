function createButton(context, name, func) {
	let button = document.createElement("button");
	button.type = "button";
	button.setAttribute("onclick", func);
	button.innerHTML = name;
	context.appendChild(button);
}

////////////////////////////////////////////////
//Product functions
////////////////////////////////////////////////

function deleteProduct(id) {
	let xhttp = new XMLHttpRequest();
	
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			document.getElementById(id).remove();
		}
	}
	
	xhttp.open("DELETE", "/product/" + id, true);
	xhttp.send();
}

function showEditForm(id) {
	document.getElementById("editForm").style.display = "block";
	let editForm = document.forms["editProductForm"];
	let productRow = document.getElementById(id);
	
	editForm.elements["id"].value = productRow.cells[0].innerHTML;
	editForm.elements["name"].value = productRow.cells[1].innerHTML;
	editForm.elements["description"].value = productRow.cells[2].innerHTML;
	editForm.elements["price"].value = productRow.cells[3].innerHTML;
	editForm.elements["prodcode"].value = productRow.cells[4].innerHTML;
}

function hideEditForm(id) {
	document.getElementById("editForm").style.display = "none";
	
}

function editProduct() {
	let xhttp = new XMLHttpRequest();
	let form = document.forms["editProductForm"];
	let product = {};

	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			console.log(this.responseText);
			if (this.responseText) {
				let data = JSON.parse(this.responseText);
				let productRow = document.getElementById(data["id"]);
				productRow.cells[0].innerHTML = data["id"];
				productRow.cells[1].innerHTML = data["name"];
				productRow.cells[2].innerHTML = data["description"];
				productRow.cells[3].innerHTML = data["price"];
				productRow.cells[4].innerHTML = data["prodcode"];
			} else {
				window.alert("Error in input");
			}
			
		}
	};
	
	product["id"] = form["id"].value;
	product["name"] = form["name"].value;
	product["description"] = form["description"].value;
	product["price"] = form["price"].value;
	product["prodcode"] = form["prodcode"].value;
	
	xhttp.open("PUT", "/product/" + product["id"], true);
	xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	xhttp.send(JSON.stringify(product));
}

function addProduct() {
	let xhttp = new XMLHttpRequest();
	let form = document.forms["addProductForm"];
	let product = {};
	
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			
			console.log(this.responseText);
			if (this.responseText) {
				let data = JSON.parse(this.responseText);
				let table = document.getElementById("table");
				let row = table.insertRow();
				row.setAttribute("id", data.id);
				
				let elements = ["id", "name", "description", "price", "prodcode"];
				
				elements.forEach(function (item, index) {
					let cell = row.insertCell(index);
					cell.appendChild(document.createTextNode(data[item]));
				});
				
				let buttonCell = row.insertCell(5);
				createButton(buttonCell, "Edit", "showEditForm(" + data["id"] + ")" );
				createButton(buttonCell, "Delete", "deleteProduct(" + data["id"] + ")");
			} else {
				window.alert("Error in input");
			}
			
		}
	};
	
	product["name"] = form["name"].value;
	product["description"] = form["description"].value;
	product["price"] = form["price"].value;
	product["prodcode"] = form["prodcode"].value;
	product["manufacturer"] = document.querySelector("[data-manid]").getAttribute("data-manid");
	
	xhttp.open("POST", "/product", true);
	xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	xhttp.send(JSON.stringify(product));
}

////////////////////////////////////////////////
// Package functions
////////////////////////////////////////////////

function addToPackage(product) {
	let xhttp = new XMLHttpRequest();
	
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			if (this.responseText == "duplicate") {
				alert("Product is already in an unconfirmed package.");
			} else if (this.responseText == "added") {
				alert("Added to an existing unconfirmed package");
			} else if (this.responseText == "created") {
				alert("Created new package");
			}
		}
	};
	
	xhttp.open("POST", "/package", true);
	xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	xhttp.send(product);
}

function sendPackage(pakage) {
	let xhttp = new XMLHttpRequest();
	
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			let container = document.querySelector("div[data-packageid=\"" + pakage + "\"]");
			let sent = container.querySelector("div.packageSent");
			sent.innerHTML = "Confirmed";
			let status = document.createElement("div");
			status.setAttribute("class", "packageStatus");
			status.innerHTML = "Not ready";
			sent.parentNode.insertBefore(status, sent.nextSibling);
			container.querySelector("div.packageButtons").remove();
			let removeCells = container.querySelectorAll("td.removeCell");
			while (removeCells.length > 0) {
				removeCells[0].remove();
			} 
			
			alert("The package is confirmed!");
			
		}
	}
	
	xhttp.open("PUT", "/package" , true);
	xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	xhttp.send(JSON.stringify({pakage}));
}

function changeQuantity(product, pakage) {
	let xhttp = new XMLHttpRequest();
	let container = document.querySelector("div[data-packageid=\""+ pakage + "\"]");
	let productel = container.querySelector("tr[data-productid=\"" + product + "\"]");
	let quantityel = productel.querySelector("input[name=\"quantity\"]");
	let quantity = quantityel.value;
	
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			
		}
	}
	
	xhttp.open("PUT", "/package" , true);
	xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	xhttp.send(JSON.stringify({product, pakage, quantity}));
}

function removeProductFromPackage(product, pakage) {
	let xhttp = new XMLHttpRequest();
	
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			let container = document.querySelector("div[data-packageid=\""+ pakage + "\"]");
			container.querySelector("tr[data-productid=\"" + product + "\"]").remove();
		}
	}
	
	xhttp.open("DELETE", "/package" , true);
	xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	xhttp.send(JSON.stringify({product, pakage}));
}

function deletePackage(pakage) {
	let xhttp = new XMLHttpRequest();
	
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			let el = document.querySelector('div[data-packageid=\"'+ pakage + '\"]');
			el.remove();
			alert("The package is deleted!");
		}
	}
	
	xhttp.open("DELETE", "/package" , true);
	xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	xhttp.send(JSON.stringify({pakage}));
}

////////////////////////////////////////////////
//Search functions
////////////////////////////////////////////////

function searchProducts() {
	let search = document.querySelector("input[name=\"product\"]").value;
	window.open("/products?s=" + search, "_self");
}

function searchManufacturers() {
	let search = document.querySelector("input[name=\"manufacturer\"]").value;
	window.open("/manufacturers?s=" + search, "_self");
}

/////////////////////////////////////////////////
//Login and register functions
/////////////////////////////////////////////////

function login() {
	let xhttp = new XMLHttpRequest();
	let form = document.forms["loginForm"];
	let user = {};
	
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			let value = this.responseText;
			if (value == "login") {
				window.alert("Error!");
			} else {
				window.open("/" + value, "_self");
			}
			
		}
	}
	
	user["email"] = form["email"].value;
	user["password"] = form["password"].value;
	user["type"] = form["type"].value;
	
	xhttp.open("POST", "/login" , true);
	xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	xhttp.send(JSON.stringify(user));
}

function registerClient() {
	let xhttp = new XMLHttpRequest();
	let form = document.forms["clientForm"];
	let client = {};
	
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			let value = this.responseText;
			console.log(value);
			if (value == "true") {
				window.alert("Registered!");
				window.open("/login", "_self");
			} else {
				window.alert("Error(s) in input");
			}
		}
	}
	
	client["email"] = form["email"].value;
	client["password"] = form["password"].value;
	client["name"] = form["name"].value;
	client["surname"] = form["surname"].value;
	client["address"] = form["address"].value;
	
	xhttp.open("POST", "/client" , true);
	xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	xhttp.send(JSON.stringify(client));
}

function registerManufacturer() {
	let xhttp = new XMLHttpRequest();
	let form = document.forms["manufacturerForm"];
	let manufacturer = {};
	
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			let value = this.responseText;
			console.log(value);
			if (value == "true") {
				window.alert("Registered!");
				window.open("/login", "_self");
			} else {
				window.alert("Error(s) in input");
			}
		}
	}
	
	manufacturer["email"] = form["email"].value;
	manufacturer["password"] = form["password"].value;
	manufacturer["name"] = form["name"].value;
	manufacturer["description"] = form["description"].value;
	manufacturer["address"] = form["address"].value;
	manufacturer["code"] = form["code"].value;
	
	xhttp.open("POST", "/manufacturer" , true);
	xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	xhttp.send(JSON.stringify(manufacturer));
}