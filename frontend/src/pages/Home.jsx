import React, { useEffect, useState } from "react";
import "../stylesheets/home.css";

function Home() {
  const [username, setUsername] = useState("");
  const [userId, setUserId] = useState(null);
  const [users, setUsers] = useState([]);
  const [selectedUserIds, setSelectedUserIds] = useState([]);
  const [amount, setAmount] = useState("");
  const [description, setDescription] = useState("");
  const [owedExpenses, setOwedExpenses] = useState([]);
  const [paidExpenses, setPaidExpenses] = useState([]);

  const authToken = localStorage.getItem("authToken");

  useEffect(() => {
    fetch("http://localhost:9090/users/me", {
      headers: { Authorization: `Basic ${authToken}` },
    })
      .then((res) => res.json())
      .then((data) => {
        setUsername(data.username);
        setUserId(data.id);
        setSelectedUserIds([data.id]);
      });

    fetch("http://localhost:9090/users", {
      headers: { Authorization: `Basic ${authToken}` },
    })
      .then((res) => res.json())
      .then((data) => setUsers(data));
  }, []);

  useEffect(() => {
    if (!userId) return;

    fetch(`http://localhost:9090/expenses/paidBy/${userId}`, {
      headers: { Authorization: `Basic ${authToken}` },
    })
      .then((res) => res.json())
      .then((data) => setPaidExpenses(data));

    fetch(`http://localhost:9090/expenses/owedBy/${userId}`, {
      headers: { Authorization: `Basic ${authToken}` },
    })
      .then((res) => res.json())
      .then((data) => setOwedExpenses(data));
  }, [userId]);

  const handleCheckboxChange = (id) => {
    if (id === userId) return;
    if (selectedUserIds.includes(id)) {
      setSelectedUserIds(selectedUserIds.filter((uid) => uid !== id));
    } else {
      setSelectedUserIds([...selectedUserIds, id]);
    }
  };

  const handleSplit = async () => {
    if (!amount || !description || selectedUserIds.length < 2) {
      alert("Please enter amount, description and select at least one other person.");
      return;
    }

    const res = await fetch("http://localhost:9090/expenses/split", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Basic ${authToken}`,
      },
      body: JSON.stringify({
        payerId: userId,
        payeeIds: selectedUserIds,
        totalAmount: parseFloat(amount),
        description,
      }),
    });

    if (res.ok) {
      alert("Expense split successfully!");
      setAmount("");
      setDescription("");
      setSelectedUserIds([userId]);

      const [owedRes, paidRes] = await Promise.all([
        fetch(`http://localhost:9090/expenses/owedBy/${userId}`, {
          headers: { Authorization: `Basic ${authToken}` },
        }),
        fetch(`http://localhost:9090/expenses/paidBy/${userId}`, {
          headers: { Authorization: `Basic ${authToken}` },
        }),
      ]);

      const owedData = await owedRes.json();
      const paidData = await paidRes.json();
      setOwedExpenses(owedData);
      setPaidExpenses(paidData);
    } else {
      alert("Error splitting expense.");
    }
  };

  return (
    <div className="modern-home">
      <header className="home-header">
        <h1>Hello, {username} 👋</h1>
        <button className="logout" onClick={() => {
          localStorage.clear();
          window.location.href = "/";
        }}>Logout</button>
      </header>

      <section className="card">
        <h2>Create a Shared Expense</h2>
        <p className="info">You're included by default</p>

        <div className="users">
          {users.map((user) => (
            <label key={user.id} className="user-chip">
              <input
                type="checkbox"
                checked={selectedUserIds.includes(user.id)}
                onChange={() => handleCheckboxChange(user.id)}
                disabled={user.id === userId}
              />
              {user.name}
            </label>
          ))}
        </div>

        <input
          type="number"
          placeholder="Amount"
          value={amount}
          onChange={(e) => setAmount(e.target.value)}
          className="input"
        />
        <input
          type="text"
          placeholder="Description"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          className="input"
        />
        <button className="primary-btn" onClick={handleSplit}>Split Expense</button>
      </section>

      <section className="card">
        <h2>People Who Owe You</h2>
        {paidExpenses.map((exp, i) => {
          const payee = users.find((u) => u.id === exp.payeeId);
          return (
            <div key={i} className="expense-row">
              <strong>{payee?.name || "Unknown"}</strong> owes you ₹{exp.amount.toFixed(2)} for <em>{exp.description}</em>
            </div>
          );
        })}
      </section>

      <section className="card">
        <h2>What You Owe</h2>
        {owedExpenses.map((exp, i) => {
          const payer = users.find((u) => u.id === exp.payerId);
          return (
            <div key={i} className="expense-row">
              You owe <strong>{payer?.name || "Unknown"}</strong> ₹{exp.amount.toFixed(2)} for <em>{exp.description}</em>
            </div>
          );
        })}
      </section>
    </div>
  );
}

export default Home;
