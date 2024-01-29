import React, { useState } from "react";
import SmallButton from "../button/SmallButton";

const BoardModal = ({ isOpen, closeModal }) => {
  const [selectedTab, setSelectedTab] = useState("segment1");

  const handleOutsideClick = (e) => {
    if (e.target.id === "my-modal") {
      closeModal();
    }
  };

  const handleTabClick = (tab) => {
    setSelectedTab(tab);
  };

  return (
    <div
      className="fixed inset-0 w-full h-full overflow-y-auto bg-gray-600 bg-opacity-50"
      id="my-modal"
      onClick={handleOutsideClick}
    >
      <div className="relative p-5 mx-auto bg-white border rounded-md shadow-lg top-20 w-96">
        <div className="bg-gray-200 rounded-lg" style={{ padding: "2px" }}>
          <div className="relative flex items-center">
            {/* Tab dividers */}
            <div className="absolute w-full">
              <div className="flex justify-between w-1/3 m-auto">
                <div
                  className="w-px h-3 transition-opacity duration-100 ease-in-out bg-gray-400 rounded-full opacity-0"
                  style={{ opacity: selectedTab === "segment3" ? 1 : 0 }}
                ></div>
                <div
                  className="w-px h-3 transition-opacity duration-100 ease-in-out bg-gray-400 rounded-full opacity-0"
                  style={{ opacity: selectedTab === "segment1" ? 1 : 0 }}
                ></div>
              </div>
            </div>

            {/* White sliding tab block */}
            <div
              className={`absolute left-0 inset-y-0 w-1/3 flex bg-white transition-all ease-in-out duration-200 transform rounded-md shadow ${
                selectedTab === "segment1"
                  ? "translate-x-0"
                  : selectedTab === "segment2"
                  ? "translate-x-full"
                  : "translate-x-fullx2"
              }`}
            ></div>

            {/* Clickable buttons */}
            <div
              className={`relative flex-1 flex text-sm font-semibold capitalize items-center justify-center cursor-pointer m-px p-px ${
                selectedTab === "segment1" ? "bg-gray-200" : ""
              }`}
              onClick={() => handleTabClick("segment1")}
            >
              열매
            </div>
            <div
              className={`relative flex-1 flex text-sm font-semibold capitalize items-center justify-center cursor-pointer m-px p-px ${
                selectedTab === "segment2" ? "bg-gray-200" : ""
              }`}
              onClick={() => handleTabClick("segment2")}
            >
              도감
            </div>
            <div
              className={`relative flex-1 flex text-sm font-semibold capitalize items-center justify-center cursor-pointer m-px p-px ${
                selectedTab === "segment3" ? "bg-gray-200" : ""
              }`}
              onClick={() => handleTabClick("segment3")}
            >
              뽑기
            </div>
          </div>
        </div>

        {/* Content based on selected tab */}
        {selectedTab === "segment1" && (
          <div>
            <p>내용1</p>
          </div>
        )}
        {selectedTab === "segment2" && (
          <div>
            <p>내용2</p>
          </div>
        )}
        {selectedTab === "segment3" && (
          <div>
            <p>내용3</p>
          </div>
        )}

        <div className="flex justify-end">
          <SmallButton onClick={closeModal} text="닫기"></SmallButton>
        </div>
      </div>
    </div>
  );
};

export default BoardModal;
