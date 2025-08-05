import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

function Login() {
  const [name, setName] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();

    const authToken = btoa(`${name}:${password}`);

    try {
      const response = await fetch("http://localhost:9090/", {
        method: "GET",
        headers: {
          "Authorization": `Basic ${authToken}`
        }
      });

      if (response.ok) {
        const message = await response.text();
        localStorage.setItem("authToken", authToken);
        localStorage.setItem("username", name);
        localStorage.setItem("password", password); // only for testing!
        alert("✅ Login successful!\n" + message);
        navigate("/home");
      } else {
        alert("❌ Invalid credentials");
      }
    } catch (error) {
      console.error("Login error:", error);
      alert("🚫 Login failed due to a network error.");
    }
  };

  return (
    <div>
      <h2>Login</h2>
      <form onSubmit={handleLogin}>
        <input type="text" placeholder="Username" value={name} onChange={(e) => setName(e.target.value)} required />
        <br />
        <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} required />
        <br />
        <button type="submit">Login</button>
      </form>
    </div>
  );
}

export default Login;
