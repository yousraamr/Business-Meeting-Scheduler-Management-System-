window.onload = () => {
	const panels = {
		overview: document.getElementById("overview-panel"),
		users: document.getElementById("user-management"),
		meetings: document.getElementById("meeting-management"),
		settings: document.getElementById("settings-section"),
	};
	Object.values(panels).forEach((p) => (p.style.display = "none"));
	function showPanel(panel) {
		Object.values(panels).forEach((p) => (p.style.display = "none"));
		panels[panel].style.display = "block";
	}

	document.getElementById("view-overview-btn").onclick = () =>
		showPanel("overview");
	document.getElementById("view-users-btn").onclick = () =>
		showPanel("users");
	document.getElementById("view-meetings-btn").onclick = () =>
		showPanel("meetings");
	document.getElementById("settings-btn").onclick = () =>
		showPanel("settings");

	function loadOverview() {
		// Fetch users and meetings data concurrently
		Promise.all([
			fetch("/users").then((res) => res.json()),
			fetch("/meetings").then((res) => res.json()),
		])
			.then(([users, meetings]) => {
				// Count the number of users and meetings
				let meetingscount = meetings.length;
				let userscount = users.length;

				// Update the meeting and user count in the HTML
				document.getElementById("meetingcount").innerHTML =
					"<strong>Total Meetings: </strong>" + meetingscount;

				document.getElementById("usercount").innerHTML =
					"<strong>Total Users: </strong>" + userscount;
			})
			.catch((error) => {
				console.error("Error fetching data:", error);
			});
	}
	// Load Users
	function loadUsers() {
		fetch("/users")
			.then((res) => res.json())
			.then((users) => {
				const tbody = document.querySelector(".user-table tbody");
				tbody.innerHTML = "";
				users.forEach((user) => {
					const tr = document.createElement("tr");
					tr.setAttribute("data-id", user.id);
					tr.innerHTML = `
              <td class="username">${user.username}</td>
              <td class="email">${user.email}</td>
              <td class="role">${user.role}</td>
              <td>
                <button class="btn small-btn edit-user-btn">Edit</button>
                <button class="btn small-btn delete-user-btn">Delete</button>
              </td>
            `;
					tbody.appendChild(tr);
				});
				totalUsers = users.length;
				updateUserTable();
				bindUserActions();
			});
	}

	// Load Meetings
	function loadMeetings() {
		fetch("/meetings")
			.then((res) => res.json())
			.then((meetings) => {
				const tbody = document.querySelector(".meeting-table tbody");
				tbody.innerHTML = "";
				meetings.forEach((meeting) => {
					const tr = document.createElement("tr");
					tr.setAttribute("data-id", meeting.id);
					tr.innerHTML = `
              <td class="title">${meeting.title}</td>
              <td class="date">${meeting.date}</td>
              <td class="time">${meeting.time}</td>
              <td class="description">${meeting.description}</td>
              <td>
                <button class="btn small-btn edit-meeting-btn">Edit</button>
                <button class="btn small-btn delete-meeting-btn">Delete</button>
              </td>
            `;
					tbody.appendChild(tr);
				});
				totalmeetings = meetings.length;
				updateMeetingTable();
				bindMeetingActions();
			});
	}

	// Pagination (Users)
	let currentPage = 1;
	const usersPerPage = 5;
	let totalUsers = 0;
	const userPrevBtn = document.getElementById("prev-btn");
	const userNextBtn = document.getElementById("next-btn");

	function updateUserTable() {
		const userRows = document.querySelectorAll(".user-table tbody tr");
		userRows.forEach((row, index) => {
			row.style.display =
				index >= (currentPage - 1) * usersPerPage &&
				index < currentPage * usersPerPage
					? "table-row"
					: "none";
		});
		userPrevBtn.disabled = currentPage === 1;
		userNextBtn.disabled = currentPage * usersPerPage >= totalUsers;
	}

	userPrevBtn.onclick = () => {
		if (currentPage > 1) {
			currentPage--;
			updateUserTable();
		}
	};

	userNextBtn.onclick = () => {
		if (currentPage * usersPerPage < totalUsers) {
			currentPage++;
			updateUserTable();
		}
	};

	let currentmeetingPage = 1;
	const meetingPerPage = 5;
	let totalmeetings = 0;
	const meetingPrevBtn = document.getElementById("prev-btn1");
	const meetingNextBtn = document.getElementById("next-btn1");

	function updateMeetingTable() {
		const meetingRows = document.querySelectorAll(
			".meeting-table tbody tr"
		);
		meetingRows.forEach((row, index) => {
			row.style.display =
				index >= (currentmeetingPage - 1) * meetingPerPage &&
				index < currentmeetingPage * meetingPerPage
					? "table-row"
					: "none";
		});
		meetingPrevBtn.disabled = currentmeetingPage === 1;
		meetingNextBtn.disabled =
			currentmeetingPage * meetingPerPage >= totalmeetings;
	}

	meetingPrevBtn.onclick = () => {
		if (currentmeetingPage > 1) {
			currentmeetingPage--;
			updateMeetingTable();
		}
	};

	meetingNextBtn.onclick = () => {
		if (currentmeetingPage * meetingPerPage < totalmeetings) {
			currentmeetingPage++;
			updateMeetingTable();
		}
	};

	// Add User
	document.querySelector("#add-user-form").onsubmit = (e) => {
		e.preventDefault();
		const user = {
			username: document.getElementById("user-username").value,
			email: document.getElementById("user-email").value,
			password: document.getElementById("user-password").value,
			role: document.getElementById("user-role").value,
		};

		fetch("/users", {
			method: "POST",
			headers: { "Content-Type": "application/json" },
			body: JSON.stringify(user),
		}).then((res) => {
			if (res.ok) {
				alert("User created!");
				loadUsers();
				loadOverview();
				document.getElementById("add-user-modal").style.display =
					"none";
			} else {
				alert("Failed to create user.");
			}
		});
	};

	// Bind User Buttons
	function bindUserActions() {
		document.querySelectorAll(".edit-user-btn").forEach((button) => {
			button.onclick = () => {
				const row = button.closest("tr");
				document.getElementById("edit-user-id").value = row.dataset.id;
				document.getElementById("edit-user-username").value =
					row.querySelector(".username").innerText;
				document.getElementById("edit-user-email").value =
					row.querySelector(".email").innerText;
				document.getElementById("edit-user-role").value =
					row.querySelector(".role").innerText;
				document.getElementById("edit-user-password").value = "";
				document.getElementById("edit-user-modal").style.display =
					"flex";
			};
		});

		document.querySelectorAll(".delete-user-btn").forEach((button) => {
			button.onclick = () => {
				const row = button.closest("tr");
				const id = row.dataset.id;
				const name = row.querySelector(".username").innerText;

				if (confirm(`Are you sure you want to delete ${name}?`)) {
					fetch(`/users/${id}`, { method: "DELETE" }).then((res) => {
						if (res.ok) {
							alert("User deleted!");
							loadUsers();
							loadOverview();
						} else {
							alert("Failed to delete user.");
						}
					});
				}
			};
		});
	}

	// Submit User Edit
	document.querySelector("#edit-user-form").onsubmit = (e) => {
		e.preventDefault();
		const id = document.getElementById("edit-user-id").value;
		const user = {
			username: document.getElementById("edit-user-username").value,
			email: document.getElementById("edit-user-email").value,
			role: document.getElementById("edit-user-role").value,
			password: document.getElementById("edit-user-password").value,
		};

		fetch(`/users/${id}`, {
			method: "PUT",
			headers: { "Content-Type": "application/json" },
			body: JSON.stringify(user),
		}).then((res) => {
			if (res.ok) {
				alert("User updated!");
				loadUsers();
				document.getElementById("edit-user-modal").style.display =
					"none";
			} else {
				alert("Failed to update user.");
			}
		});
	};

	// Meeting Buttons
	function bindMeetingActions() {
		document.querySelectorAll(".edit-meeting-btn").forEach((button) => {
			button.onclick = () => {
				const row = button.closest("tr");
				document.getElementById("edit-meeting-id").value =
					row.dataset.id;
				document.getElementById("edit-meeting-title").value =
					row.querySelector(".title").innerText;
				document.getElementById("edit-meeting-date").value =
					row.querySelector(".date").innerText;
				document.getElementById("edit-meeting-time").value =
					row.querySelector(".time").innerText;
				document.getElementById("edit-meeting-description").value =
					row.querySelector(".description").innerText;
				document.getElementById("edit-meeting-modal").style.display =
					"flex";
			};
		});

		document.querySelectorAll(".delete-meeting-btn").forEach((button) => {
			button.onclick = () => {
				const row = button.closest("tr");
				const id = row.dataset.id;
				const title = row.querySelector(".title").innerText;

				if (
					confirm(
						`Are you sure you want to delete meeting "${title}"?`
					)
				) {
					fetch(`/admin/meetings/delete/${id}`).then((res) => {
						if (res.ok) {
							alert("Meeting deleted!");
							loadMeetings();
							loadOverview();
						} else {
							alert("Failed to delete meeting.");
						}
					});
				}
			};
		});
	}

	// Modal Controls
	document.getElementById("add-user-btn").onclick = () =>
		(document.getElementById("add-user-modal").style.display = "flex");
	document.getElementById("close-add-user-modal").onclick = () =>
		(document.getElementById("add-user-modal").style.display = "none");
	document.getElementById("close-edit-user-modal").onclick = () =>
		(document.getElementById("edit-user-modal").style.display = "none");

	document.getElementById("add-meeting-btn").onclick = () =>
		(document.getElementById("add-meeting-modal").style.display = "flex");
	document.getElementById("close-add-meeting-modal").onclick = () =>
		(document.getElementById("add-meeting-modal").style.display = "none");
	document.getElementById("close-edit-meeting-modal").onclick = () =>
		(document.getElementById("edit-meeting-modal").style.display = "none");

	window.onclick = (e) => {
		if (e.target === document.getElementById("add-user-modal"))
			document.getElementById("add-user-modal").style.display = "none";
		if (e.target === document.getElementById("edit-user-modal"))
			document.getElementById("edit-user-modal").style.display = "none";
		if (e.target === document.getElementById("add-meeting-modal"))
			document.getElementById("add-meeting-modal").style.display = "none";
		if (e.target === document.getElementById("edit-meeting-modal"))
			document.getElementById("edit-meeting-modal").style.display =
				"none";
	};

	// Add Meeting
	document.querySelector("#add-meeting-modal form").onsubmit = (e) => {
		e.preventDefault();
		const meeting = {
			title: document.getElementById("meeting-title").value,
			date: document.getElementById("meeting-date").value,
			time: document.getElementById("meeting-time").value,
			description: document.getElementById("meeting-description").value,
		};

		fetch("/admin/meetings/save", {
			method: "POST",
			headers: { "Content-Type": "application/x-www-form-urlencoded" },
			body: new URLSearchParams(meeting),
		}).then((res) => {
			if (res.ok) {
				alert("Meeting created!");
				document.getElementById("add-meeting-modal").style.display =
					"none";
				showPanel("meetings");
				loadMeetings();
				loadOverview();
			} else {
				alert("Failed to create meeting.");
			}
		});
	};

	// Edit Meeting
	document.querySelector("#edit-meeting-modal form").onsubmit = (e) => {
		e.preventDefault();
		const id = document.getElementById("edit-meeting-id").value;
		const meeting = {
			title: document.getElementById("edit-meeting-title").value,
			date: document.getElementById("edit-meeting-date").value,
			time: document.getElementById("edit-meeting-time").value,
			description: document.getElementById("edit-meeting-description")
				.value,
		};

		fetch(`/admin/meetings/update/${id}`, {
			method: "POST",
			headers: { "Content-Type": "application/x-www-form-urlencoded" },
			body: new URLSearchParams(meeting),
		}).then((res) => {
			if (res.ok || res.redirected) {
				alert("Meeting updated!");
				showPanel("meetings");
				loadMeetings();
				document.getElementById("edit-meeting-modal").style.display =
					"none";
			} else {
				alert("Failed to update meeting.");
			}
		});
	};

	// Initial load

	loadUsers();
	loadMeetings();
	loadOverview();
};
