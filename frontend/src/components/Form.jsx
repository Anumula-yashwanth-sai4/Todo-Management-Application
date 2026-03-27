import { useState, useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";
export function Form() {
   const [task, setTask] = useState({
       title: "",
       description: "",
       completed: false
   });
   const navigate = useNavigate();
   const { id } = useParams();
   
   useEffect(() => {
       if (id) {
           axios.get("http://localhost:8081/todo/showTasks")
               .then(response => {
                   const found = response.data.find(t => t.id == id);
                   if (found) setTask(found);
               })
               .catch(err => console.log(err));
       }
   }, [id]);
   
   const handleChange = (event) => {
       const { name, value, type, checked } = event.target;
       setTask({
           ...task,
           [name]: type === "checkbox" ? checked : value
       });
   };
   
   const handleSubmit = (e) => {
       e.preventDefault();
       const apiResponse = id
           ? axios.patch(`http://localhost:8081/todo/editTask/${id}`, task)
           : axios.post("http://localhost:8081/todo/add", task);
       apiResponse
           .then(() => navigate("/lists"))
           .catch(err => console.log(err));
   };
   return (
<>
<div className="container mt-5 pt-5">

<h2 className="text-center mb-4">
  {id ? "Edit Task" : "Add Task"}
</h2>

<form onSubmit={handleSubmit} className="w-50 mx-auto">
                   
<div className="mb-3">
<label className="form-label">Title</label>
<input
                           className="form-control"
                           type="text"
                           name="title"
                           value={task.title}
                           onChange={handleChange}
                       />
</div>
                   
<div className="mb-3">
<label className="form-label">Description</label>
<input
                           className="form-control"
                           type="text"
                           name="description"
                           value={task.description}
                           onChange={handleChange}
                       />
</div>
                   
{id && (
<div className="form-check mb-3">
<input
           className="form-check-input"
           type="checkbox"
           name="completed"
           checked={task.completed}
           onChange={handleChange}
       />
<label className="form-check-label">
           Completed
</label>
</div>
)}
<button type="submit" className="btn btn-info me-3">
  Submit
</button>

{id && (
  <button
    type="button"
    className="btn btn-info"
    onClick={() => navigate("/lists")}
  >
    Back to List
  </button>
)}

</form>
</div>
</>
   );
}