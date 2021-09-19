const url = "http://localhost:8080/api/users" // get, post, patch
const urlPrincipal = "http://localhost:8080/api/user" // get principal


const tableUsers = document.querySelector("#tab-users")
const tablePrincipal = document.querySelector("#tab-principal")
const emailPrincipal = document.querySelector("#emailPrincipal")
const rolesPrincipal = document.querySelector("#rolesPrincipal")
//Edit
const editModalForm = document.querySelector("#editModal #updateForm")
const btnEditForm = document.querySelector("#btnEditForm")
//Delete
const deleteModalForm = document.querySelector("#deleteModal #deleteForm")
const btnDeleteForm = document.querySelector("#btnDeleteForm")
//Add
const addNewUserForm = document.querySelector("#addNewUser")
const btnAddNewUser = document.querySelector("#btnAdd")

//Create users table
async function usersTable() {
    const response = await fetch(url)
    const data = await response.json()

    let temp = ""
    data.forEach((u) => {

        const rolesArr = u.roles.map(r => r.name)
        const roles = rolesArr.toString().replace(/,/g, ' ');

        temp += `<tr id="dataId${u.id}">
                <td>${u.id}</td>
                <td>${u.name}</td>
                <td>${u.surname}</td>
                <td>${u.age}</td>
                <td>${u.email}</td>
                <td>${roles}</td>
                <td><button type="button" class="btn btn-info" data-bs-toggle="modal" data-bs-target="#editModal">Edit</button></td>
                <td><button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteModal">Delete</button></td>                                                                                            
            </tr>`
    })
    tableUsers.innerHTML = temp

    data.forEach(u => {
        const btnEdit = document.querySelector(`#dataId${u.id} .btn-info`);
        btnEdit.addEventListener('click', () => {

            editModalForm.id.value = u.id
            editModalForm.name.value = u.name
            editModalForm.surname.value = u.surname
            editModalForm.age.value = u.age
            editModalForm.email.value = u.email
        })

        const btnDelete = document.querySelector(`#dataId${u.id} .btn-danger`);
        btnDelete.addEventListener('click', () => {

            deleteModalForm.id.value = u.id
            deleteModalForm.name.value = u.name
            deleteModalForm.surname.value = u.surname
            deleteModalForm.age.value = u.age
            deleteModalForm.email.value = u.email
        })
    })
}

usersTable()

//Creat user info page
async function userInfo() {
    const response = await fetch(urlPrincipal)
    const user = await response.json()

    const rolesArr = user.roles.map(r => r.name)
    const roles = rolesArr.toString().replace(/,/g, ' ');
    tablePrincipal.innerHTML = `<tr id="data-principal">
                                    <td>${user.id}</td>
                                    <td>${user.name}</td>
                                    <td>${user.surname}</td>
                                    <td>${user.age}</td>
                                    <td>${user.email}</td>
                                    <td>${roles}</td>
                                </tr>`

    emailPrincipal.innerHTML = user.email
    rolesPrincipal.innerHTML = roles
}

userInfo()

//Post request
btnAddNewUser.addEventListener("click", async e => {
    e.preventDefault()

    let selected = Array.from(addNewUserForm.roles.options)
        .filter(option => option.selected)
        .map(option => option.value);

    await fetch(url, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            email: addNewUserForm.email.value,
            password: addNewUserForm.password.value,
            name: addNewUserForm.name.value,
            surname: addNewUserForm.surname.value,
            age: addNewUserForm.age.value,
            roles: selected
        })
    }).then(() => {
        addNewUserForm.name.value = ""
        addNewUserForm.surname.value = ""
        addNewUserForm.age.value = ""
        addNewUserForm.email.value = ""
        addNewUserForm.password.value = ""

        Array.from(addNewUserForm.roles.options)
            .filter(option => option.selected)
            .map(option => option.selected = false);

        usersTable()
    })
})

//Delete request
btnDeleteForm.addEventListener("click", async e => {
    e.preventDefault()
    const id = deleteModalForm.id.value
    const tableRow = document.querySelector(`#dataID${id}`)
    await fetch(`http://localhost:8080/api/users/${id}`, {
        method: "DELETE"
    }).then(() => {
        addNewUserForm.password.value = ""
        document.querySelector("#btnCloseDelete").click()
        usersTable()
    })
})

//Patch request
btnEditForm.addEventListener("click", async e => {
    e.preventDefault()

    let selected = Array.from(editModalForm.roles.options)
        .filter(option => option.selected)
        .map(option => option.value);

    await fetch(url, {
        method: "PATCH",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: editModalForm.id.value,
            email: editModalForm.email.value,
            password: editModalForm.password.value,
            name: editModalForm.name.value,
            surname: editModalForm.surname.value,
            age: editModalForm.age.value,
            roles: selected
        })
    }).then(() => {
        editModalForm.password.value = ""
        Array.from(editModalForm.roles.options)
            .filter(option => option.selected)
            .map(option => option.selected = false);
        document.querySelector("#btnCloseEdit").click()
        usersTable()
        userInfo()
    })
})