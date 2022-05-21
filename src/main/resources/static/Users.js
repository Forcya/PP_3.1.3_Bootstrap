const url = `http://localhost:8080/api/users`
const allUsersTableBody = document.getElementById('all-users-table-body');
let users;

// async function fetchRequest () {
//     try {  //await - равносильно тому, что мы обрабатываем Promise (используется вместо .than())
//         const response = await fetch(url)  //await, так как fetch тоже возвращает промис. Получаем в переменную то, что возвращает fetch (response)
//         return await response.json()  //await заставляет следующую строку кода ждать, пока выполнится эта строка с await
//         //console.log(`Data`, data)  //выводим в консоль Дату
//     } catch (e) {
//         console.error(e)
//     } finally {
//         console.log(`finally`)
//     }
//
// }



async function loadIntoTablesBody(url, tableBody) {
    const response = await fetch(url);
    users = await response.json();

    tableBody.innerHTML = ''

    for (const user of users) {
        const bodyElement = document.createElement("tr")
        bodyElement.id = user.id
        bodyElement.appendChild(createTd(user.id));
        bodyElement.appendChild(createTd(user.name));
        bodyElement.appendChild(createTd(user.surname));
        bodyElement.appendChild(createTd(user.age));
        bodyElement.appendChild(createTd(user.email));
        bodyElement.appendChild(createTd(user.username));
        bodyElement.appendChild(createTd(`${user.namesOfRoles.join(', ')}`));
        const editCell = document.createElement("td");
        editCell.innerHTML = `
            <button type="button" onClick="editModalOpen(event, ${user.id})" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#editModal">Edit</button>
        `;
        bodyElement.appendChild(editCell);

        const deleteCell = document.createElement("td");
        deleteCell.innerHTML = `
            <button type="button" onClick="deleteModalOpen(event, ${user.id})" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteModal">Delete</button>
        `;
        bodyElement.appendChild(deleteCell);
        tableBody.appendChild(bodyElement)
    }

    // let htmlString = ``
    // for (let i = 0; i < users.length; i++) { //Создаем цикл
    //     htmlString += `<tr>`;
    //     htmlString += `<td>` + users[i].id + `</td>`;
    //     htmlString += `<td>` + users[i].name + `</td>`;
    //     htmlString += `<td>` + users[i].surname + `</td>`;
    //     htmlString += `<td>` + users[i].age + `</td>`;
    //     htmlString += `<td>` + users[i].email + `</td>`;
    //     htmlString += `<td>` + users[i].username + `</td>`;
    //     htmlString += `<td>`;
    //     for (let a = 0; a < users[i].roles.length; a++) {
    //         htmlString += users[i].roles[a].value + ` `;
    //     }
    //     htmlString += `</td>`;
    //     //Создаем кнопки
    //     htmlString += `<td><button class = "btn btn-info btn-sm" id = "editBtn">Edit</button></td>`;
    //     htmlString += `<td><button class = "btn btn-danger btn-sm" id = "deleteBtn">Delete</button></td>`;
    //
    //
    //     allUsersTableBody.innerHTML = htmlString; //Добавляем строку в HTML
    //
    // }
}

function createTd(user) {
    const newElement = document.createElement("td");
    newElement.textContent = user;
    return newElement;
}

loadIntoTablesBody(url, allUsersTableBody);

function editModalOpen(e, id) {
    e.preventDefault()
    fetch('http://localhost:8080/api/users/' + id, {
        method: 'GET'
    })
        .then(res => res.json())
        .then((data) => {
            let controlId = document.getElementById('idEdit')
            let controlName = document.getElementById('nameEdit')
            let controlSurname = document.getElementById('surnameEdit')
            let controlAge = document.getElementById('ageEdit')
            let controlEmail = document.getElementById('emailEdit')
            let controlUsername = document.getElementById('usernameEdit')
            let controlPassword = document.getElementById('passwordEdit')
            let controlRoles = document.getElementById('rolesEdit')
            let optionSelect = controlRoles.getElementsByTagName('option')

            reSelectRolesOption(optionSelect, data);
            fillValuesFromData(controlId, controlName, controlSurname, controlAge, controlEmail, controlUsername, data)
            controlPassword.value = data.password
        })
}

function fillValuesFromData(controlId, controlName, controlSurname, controlAge, controlEmail, controlUsername, data) {
    controlId.value = data.id
    controlName.value = data.name
    controlSurname.value = data.surname
    controlAge.value = data.age
    controlEmail.value = data.email
    controlUsername.value = data.username
}

function deleteModalOpen(e, id) {
    e.preventDefault()
    fetch('http://localhost:8080/api/users/' + id, {
        method: 'GET'
    })
        .then(res => res.json())
        .then((data) => {
            let controlDelId = document.getElementById('idDelete')
            let controlDelName = document.getElementById('nameDelete')
            let controlDelSurname = document.getElementById('surnameDelete')
            let controlDelAge = document.getElementById('ageDelete')
            let controlDelEmail = document.getElementById('emailDelete')
            let controlDelUsername = document.getElementById('usernameDelete')
            let controlRoles = document.getElementById('rolesDelete')
            let optionSelect = controlRoles.getElementsByTagName('option')

            reSelectRolesOption(optionSelect, data)
            fillValuesFromData(controlDelId, controlDelName, controlDelSurname, controlDelAge, controlDelEmail, controlDelUsername, data)
        })
}

function sendDeleteForm(e) {
    e.preventDefault()
    let form = document.getElementById('deleteForm')
    let id = form.querySelector('#idDelete').value
    fetch(`http://localhost:8080/api/users/`+id, {
        method: 'DELETE'
    })
    let row = document.getElementById(`${id}`)
    row.remove()
    document.getElementById('deleteFormClose').click()
}

function reSelectRolesOption(optionSelect, data) {
    for (let i = 0; i < optionSelect.length; i++) {
        optionSelect[i].selected = false
    }
    for (let i = 0; i < optionSelect.length; i++) {
        data.roles.forEach(role => {
            if (role.name.includes(optionSelect[i].id)) {
                optionSelect[i].selected = true
            }
        })
    }
}

async function sendSaveForm(e) {
    e.preventDefault()

    let form = document.getElementById('saveForm')
    let formData = new FormData(form)
    let values = Object.fromEntries(formData.entries())

    let option = form.querySelectorAll('option')
    let roleArr = []

    for (let i = 0; i < option.length; i++) {
        if (option[i].selected === true) {
            let eachRole = {}
            eachRole.id = option[i].value
            roleArr.push(eachRole)
        }
    }
    values.roles = roleArr

    await fetch(url, {
        method: 'POST',
        headers: {
            // 'Accept': 'application/json',
            'Content-Type': 'application/json',
            // 'Referer': null
        },
        body: JSON.stringify(values)
    }).then(form.reset())
    loadIntoTablesBody(url, allUsersTableBody);
    document.getElementById('nav-users-table-tab').click()
}

async function sendEditForm(e) {
    e.preventDefault()
    let form = document.getElementById('editForm')
    let formData = new FormData(form)
    let values = Object.fromEntries(formData.entries())
    let option = form.querySelectorAll('option')
    let roleArr = []

    for (let i = 0; i < option.length; i++) {
        if (option[i].selected === true) {
            let eachRole = {}
            eachRole.id = option[i].value
            roleArr.push(eachRole)
        }
    }

    values.roles = roleArr
    await fetch(url, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(values)
    }).then(form.reset())
    loadIntoTablesBody(url, allUsersTableBody);
    document.getElementById('editClose').click()
}
