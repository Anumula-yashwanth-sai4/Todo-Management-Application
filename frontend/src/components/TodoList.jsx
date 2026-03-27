import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

export function TodoList() {
  const [tasks, setTasks] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    axios
      .get("http://localhost:8081/todo/showTasks")
      .then(res => setTasks(res.data))
      .catch(err => console.log(err));
  }, []);

  const handleDelete = id => {
    if (window.confirm("Delete task?")) {
      axios
        .delete(`http://localhost:8081/todo/delete/${id}`)
        .then(() => setTasks(tasks.filter(t => t.id !== id)))
        .catch(err => console.log(err));
    }
  };

  const handleToggle = task => {
    axios
      .patch(`http://localhost:8081/todo/statuscompleted/${task.id}`, {
        ...task,
        completed: !task.completed
      })
      .then(() =>
        setTasks(
          tasks.map(t =>
            t.id === task.id ? { ...t, completed: !t.completed } : t
          )
        )
      );
  };

  const handleEdit = id => navigate(`/edit/${id}`);

  return (
    <div className="container mt-5 pt-5">
      <table className="table table-striped w-75 mx-auto">
        <thead>
          <tr>
            <th>Title</th>
            <th>Created At</th>
            <th>Status</th>
            <th>Edit</th>
            <th>Delete</th>
          </tr>
        </thead>
        <tbody>
          {tasks.map(task => (
            <tr key={task.id}>
              <td>{task.title}</td>
              <td>{new Date(task.createdAt).toLocaleString()}</td>
              <td>
                <button
                  className={`btn ${
                    task.completed ? "btn-success" : "btn-warning"
                  }`}
                  onClick={() => handleToggle(task)}
                >
                  {task.completed ? "Done" : "Pending"}
                </button>
              </td>
              <td>
                <button
                  className="btn btn-secondary"
                  onClick={() => handleEdit(task.id)}
                >
                  Edit
                </button>
              </td>
              <td>
                <button
                  className="btn btn-danger"
                  onClick={() => handleDelete(task.id)}
                >
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
