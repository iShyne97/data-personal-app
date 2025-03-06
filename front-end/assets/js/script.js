$(document).ready(function () {

    const apiUrl = "http://localhost:8090/api/data";
    let persons = [];

    function showFieldAlert(elementId, message, type) {
        let alertHTML = `<div class="alert alert-${type} p-2 mt-1" role="alert">${message}</div>`;
        $("#" + elementId).html(alertHTML);
    }

    function loadData() {
        $.get(apiUrl, function (response) {
            persons = response.data;
            renderTable(persons);
        }).fail(function (xhr, status, error) {
            console.error("Gagal mengambil data:", status, error);
        });
    }

    function renderTable(data) {
        let tableRows = "";
        data.forEach((person, index) => {
            tableRows += `
                <tr data-id="${person.nik}">
                    <td>${index + 1}</td>
                    <td>${person.nik}</td>
                    <td>${person.fullname}</td>
                    <td>${person.age}</td>
                    <td>${person.gender}</td>
                    <td>${formatDate(person.birth)}</td>
                    <td>${person.address}</td>
                    <td>${person.country}</td>
                    <td>
                        <button class="btn-text detail">Detail</button>
                        <button class="btn-text edit">Edit</button>
                        <button class="btn-text hapus">Hapus</button>    
                    </td>
                </tr>
            `;
        });

        $("table tbody").html(tableRows);
    }

    function validateInput() {
        let nik = $("#nik-form").val().trim();
        let nama = $("#fullname").val().trim();
        let isValid = true;

        $("#alertNik").html("");
        $("#alertName").html("");

        if (nik === "") {
            showFieldAlert("alertNik", "NIK tidak boleh kosong!", "danger");
            isValid = false;
        } else if (!nik.match(/^\d+$/)) {
            showFieldAlert("alertNik", "NIK hanya boleh berisi angka!", "warning");
            isValid = false;
        } else if (nama === "") {
            showFieldAlert("alertName", "Nama tidak boleh kosong!", "danger");
            isValid = false;
        }

        $("#saveData").prop("disabled", !isValid);
    }

    $("#nik-form, #fullname").on("input", function () {
        validateInput();
    });

    $("#btn-form").click(function (event) {
        event.preventDefault();
        $("#addDataModal").modal("show");
        $("#addDataForm")[0].reset();
        $("#alertNik").html("");
        $("#alertName").html("");
    });

    $("#saveData").click(function () {
        let nik = $("#nik-form").val().trim();
        let isEdit = $("#nik-form").prop("disabled");
        
        let data = {
            fullname: $("#fullname").val().trim(),
            gender: $("input[name='gender']:checked").val(),
            birth: $("#ttl").val(),
            address: $("#modalAlamat").val().trim(),
            country: $("#modalNegara").val()
        };
    
        let url = isEdit ? `${apiUrl}/${nik}` : apiUrl; 
        let method = isEdit ? "PUT" : "POST";
    
        console.log(`🔵 ${isEdit ? "Mengupdate" : "Menambahkan"} data dengan NIK:`, nik);
        console.log("🔵 Data yang dikirim:", data);
        console.log("🔵 URL:", url);
        console.log("🔵 Method:", method);
    
        $.ajax({
            url: url,
            method: method,
            contentType: "application/json",
            data: JSON.stringify(isEdit ? data : { nik, ...data }), 
            success: function (response) {
                $("#addDataModal").modal("hide");
                $("#addDataForm")[0].reset();
    
                let message = isEdit ? "✅ Data berhasil diperbarui!" : "✅ Data berhasil ditambahkan!";
                $("#successMessage").html(message).fadeIn();
                setTimeout(() => $("#successMessage").fadeOut(), 3000);
    
                loadData();
            },
            error: function (xhr) {
                console.error("🔴 Error dari server:", xhr.responseText);
                try {
                    let response = JSON.parse(xhr.responseText);
                    alert(response.message || "Terjadi kesalahan pada server!");
                } catch (e) {
                    alert("❌ Terjadi kesalahan saat memproses response!");
                }
            }
        });
    });
    

    $(document).on("click", ".btn-text.edit", function () {
        let nik = $(this).closest("tr").data("id");
        let person = persons.find(p => p.nik === nik);
    
        if (!person) return;
    
        $("#nik-form").val(person.nik).prop("disabled", true);
        $("#fullname").val(person.fullname);
        $("#ttl").val(person.birth);
        $("#modalAlamat").val(person.address);
        $("#modalNegara").val(person.country);
        $(`input[name='gender'][value='${person.gender}']`).prop("checked", true);
    
        $("#saveData").text("Update");
    
        $("#addDataModal").modal("show");
    });
    
    $("#addDataModal").on("hidden.bs.modal", function () {
        $("#nik-form").prop("disabled", false).val(""); 
        $("#saveData").text("Simpan Data"); 
    });
    

    $(document).on("click", ".btn-text.hapus", function () {
        let nik = $(this).closest("tr").data("id");
        let konfirmasi = confirm("Apakah yakin ingin menghapus data dengan NIK: " + nik + "?");

        if (konfirmasi) {
            $.ajax({
                url: apiUrl + "/" + nik,
                type: "DELETE",
                success: function (response) {
                    $("#successMessage").html("✅ Data berhasil dihapus!").fadeIn();
                    setTimeout(() => $("#successMessage").fadeOut(), 3000);
                    loadData();
                },
                error: function (xhr) {
                    alert("Gagal menghapus data!");
                    console.error(xhr.responseText);
                }
            });
        }
    });

    $(document).on("click", ".btn-text.detail", function () {
        let nik = $(this).closest("tr").data("id");
        let person = persons.find(p => p.nik === nik);
    
        if (!person) return;
    
        $("#nik-form").val(person.nik).prop("disabled", true);
        $("#fullname").val(person.fullname).prop("disabled", true);
        $("#ttl").val(person.birth).prop("disabled", true);
        $("#modalAlamat").val(person.address).prop("disabled", true);
        $("#modalNegara").val(person.country).prop("disabled", true);
        $(`input[name='gender'][value='${person.gender}']`).prop("disabled", true);

        $("#addDataModal").modal("show");
    });

    $("#back").click(function (){
        $("nik-form").prop("disabled", false);
        $("#fullname").prop("disabled", false);
        $("#ttl").prop("disabled", false);
        $("#modalAlamat").prop("disabled", false);
        $("#modalNegara").prop("disabled", false);
        $(`input[name='gender']`).prop("disabled", false);
        $("#addDataModal").modal("hide");
        $("#addDataForm")[0].reset();
    });

    

    $("#searchBtn").click(function () {
        let searchNik = $("#nik").val().trim();
        let searchName = $("#name").val().trim();
    
        if (searchNik === "" || searchName === "") {
            $("#warningMessage").html("⚠️ Masukkan NIK dan Nama untuk melakukan pencarian!").fadeIn().delay(3000).fadeOut();
            return;
        }
    
        $.get(apiUrl + `/find?nik=${searchNik}&fullname=${encodeURIComponent(searchName)}`)
            .done(function (response) {
                if (response.data) {
                    renderTable([response.data]);
                } else {
                    renderTable([]);
                }
            })
            .fail(function (xhr) {
                renderTable([]); 
                console.error("Error:", xhr);
            });
    });
    
    // Event listener buat reset tabel kalau input dikosongkan
    $("#nik, #name").on("input", function () {
        let searchNik = $("#nik").val().trim();
        let searchName = $("#name").val().trim();
    
        if (searchNik === "" && searchName === "") {
            loadData(); 
        }
    });
    

    function formatDate(dateString) {
        if (!dateString) return "-";
    
        let date = new Date(dateString);
        if (isNaN(date)) return dateString;
    
        return new Intl.DateTimeFormat("id-ID", { day: "2-digit", month: "short", year: "numeric" }).format(date);
    }

    loadData();
});
