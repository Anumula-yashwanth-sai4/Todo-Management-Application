import { TodoList } from "../components/TodoList";

export function Lists() {
  return (
    <div
      style={{
        backgroundColor: "#F3E5F5", 
        minHeight: "93vh",
        paddingTop: "80px"
      }}
    >
      <TodoList />
    </div>
  );
}