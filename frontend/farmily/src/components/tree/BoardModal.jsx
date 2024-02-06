  import React, { useState } from "react";
  import SmallButton from "../button/SmallButton";
  import Draw from "./Draw";
  import Collection from "./Collection";
  import Fruits from "./Fruits";

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
      <>
        {isOpen && (
          <div
            className="fixed inset-0 flex items-center justify-center z-50 ml-[12%] px-[20%] mb-40"
            id="my-modal"
            onClick={handleOutsideClick}
          >
            <div className="relative p-5 mx-auto bg-white border rounded-md shadow-lg top-20 w-96 h-96 flex flex-col">
              <div className="bg-gray-200 rounded-lg mb-5" style={{ padding: "2px" }}>
                <div className="relative flex items-center">
                  {/* Tab dividers */}
                  <div className="absolute w-full">
                    <div className="flex justify-between w-1/3 m-auto">
                      {["segment1", "segment2", "segment3"].map((segment) => (
                        <div
                          key={segment}
                          className="w-px h-3 transition-opacity duration-100 ease-in-out bg-gray-400 rounded-full"
                          style={{
                            opacity: selectedTab === segment ? 1 : 0.4, // 선택된 탭은 투명도 1, 나머지 탭은 투명도 0.5
                          }}
                        ></div>
                      ))}
                    </div>
                  </div>

                  {/* White sliding tab block */}
                  <div
                    className={`absolute left-0 inset-y-0 w-1/3 flex bg-white transition-all ease-in-out duration-200 transform rounded-md shadow`}
                    style={{
                      transform: `translateX(${
                        selectedTab === "segment1"
                          ? "0"
                          : selectedTab === "segment2"
                          ? "100%"
                          : "200%"
                      })`,
                    }}
                  ></div>

                  {/* Clickable buttons */}
                  {["segment1", "segment2", "segment3"].map((segment) => (
                    <div
                      key={segment}
                      className={`relative flex-1 flex text-sm font-semibold capitalize items-center justify-center cursor-pointer m-px p-px ${
                        selectedTab === segment ? "bg-gray-200" : ""
                      }`}
                      onClick={() => handleTabClick(segment)}
                      style={{
                        opacity: selectedTab === segment ? 1 : 0.5, // 선택된 탭은 투명도 1, 나머지 탭은 투명도 0.5
                      }}
                    >
                      {segment === "segment1" ? "열매" : segment === "segment2" ? "도감" : "뽑기"}
                    </div>
                  ))}
                </div>
              </div>

              {/* Content based on selected tab */}
              {selectedTab === "segment1" && (
                <div className="overflow-auto max-h-[calc(100vh-10rem)]">
                  <Fruits />
                </div>
              )}
              {selectedTab === "segment2" && (
                 <div className="overflow-auto max-h-[calc(100vh-10rem)]">
                  <Collection />
                </div>
              )}
              {selectedTab === "segment3" && (
                <div className="overflow-auto max-h-[calc(100vh-10rem)]">
                  <Draw />
                </div>
              )}
             <div className="mt-auto">

              <div className="flex justify-end">
                <span onClick={closeModal}><SmallButton text="닫기" /></span>
              </div>
             </div>
            </div>
          </div>
        )}
      </>
    );
  };

  export default BoardModal;
