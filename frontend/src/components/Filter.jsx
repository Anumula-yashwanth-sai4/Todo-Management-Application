import axios from "axios";
import { useState } from "react";

export function Filter() {
  const [tasks, setTasks]=useState([]);
  const [status, setStatus]=useState("true");
  const [error,setError]=useState("");

  const handleSearch= ()=>{
  setError("");     
  setTasks([]);     

  axios.get(`http://localhost:8081/todo/filtered/${status}`).then(res=> { setTasks(res.data); })
    .catch(err => { console.log(err);
    setError("Failed to show the status");
    setTasks([]); 
    });
};

  return (
    <div
      className="d-flex justify-content-center align-items-start"
      style={{ minHeight: "93vh", paddingTop: "80px", backgroundColor:"#F3E5F5" }}
    >

      <div className="card shadow-lg w-50">
        <div className="card-header bg-primary text-white text-center">
          <h4>Filter Tasks</h4>
        </div>

        <div className="card-body">
          <div className="d-flex gap-3 justify-content-center mb-4">
            <select
              className="form-select w-50"
              value={status}
              onChange={e => setStatus(e.target.value)}
            >
              <option value="true">Completed</option>
              <option value="false">Pending</option>
            </select>

            <button className="btn btn-success px-4" onClick={handleSearch}>
              Search
            </button>
          </div>
          {error && (<div className="alert alert-danger text-center">
            {error}
            </div>)}

{!error && tasks.length > 0 && (
  <ul className="list-group">
    {tasks.map(task => (
      <li
        key={task.id}
        className={`list-group-item d-flex justify-content-between align-items-center`} >
        <span>{task.title}</span>
        <small className="text-muted">
          {new Date(task.createdAt).toLocaleString()}
        </small>
      </li>
    ))}
  </ul>
)}         
        </div>
      </div>
    </div>
  );
}