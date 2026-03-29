export function Home() {
  return (
    <div
      className="home-bg d-flex justify-content-center align-items-center"
      style={{ backgroundColor: "#F3E8F7", minHeight: "93vh" }}>
    
      <div
        className="home-box text-center p-5"
        style={{
          backgroundColor: "#FFFFFF",
          maxWidth: "600px",
          width: "100%",
          borderRadius: "16px",
          boxShadow: "0 20px 50px rgba(0,0,0,0.12)",
          border: "1px solid #E6D6F0"
        }}
      >
        <h1
          className="fw-bold mb-3"
          style={{ color: "#4B145B", fontSize: "3rem" }}
        >
          Taskarooo
        </h1>

        <p
          className="mb-4"
          style={{ color: "#6B5B73", fontSize: "1.1rem" }}
        >
          A simple way to organize your tasks,<br />
          stay focused, and finish your day calmly.
        </p>

        <div
          style={{
            width: "80px",
            height: "4px",
            backgroundColor: "#D1B3E0",
            margin: "24px auto",
            borderRadius: "2px"
          }}
        ></div>

        <p
          style={{
            letterSpacing: "3px",
            fontWeight: "600",
            color: "#4B145B",
            fontSize: "0.9rem"
          }}
        >
          PLAN • TRACK • COMPLETE
        </p>
      </div>
      </div>
  );}
