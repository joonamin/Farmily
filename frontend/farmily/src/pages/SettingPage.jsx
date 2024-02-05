import { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import { CopyToClipboard } from 'react-copy-to-clipboard';

export default function SettingPage() {
  const family = useSelector((state) => state.family.value);
  const [familyName, setFamilyName] = useState(family.name);
  const [motto, setMotto] = useState(family.motto);
  const [invitationCode, setInvitationCode] = useState(family.invitationCode);

  const handleNameChange = (e) => {
    setFamilyName(e.target.value);
  };
  const handleMottoChange = (e) => {
    setMotto(e.target.value);
  };
  return (
    <>
      <h1>설정 페이지</h1>
      <Link to="/createtree">가족 생성하기</Link>
      <div className="w-full h-5/6 m-auto pt-10">
        <div className="w-full flex justify-around mb-10 h-12">
          <p className="w-1/4">가족 이름</p>
          <div className="border-4 border-black rounded-md p-1 w-1/2 pl-4 flex justify-between">
            <input
              value={familyName}
              type="text"
              onChange={handleNameChange}
              className="w-5/6"
            />
            <button className="bg-gray-300 px-4 w-20">저장</button>
          </div>
        </div>

        <div className="w-full flex justify-around mb-10 h-12">
          <p className="w-1/4">가훈</p>
          <div className="border-4 border-black rounded-md p-1 w-1/2 pl-4 flex justify-between">
            <input
              type="text"
              value={motto}
              onChange={handleMottoChange}
              className="w-5/6"
            />
            <button className="bg-gray-300 px-4 w-20">저장</button>
          </div>
        </div>

        <div className="w-full flex justify-around mb-10 h-12">
          <p className="w-1/4">초대코드</p>
          <div className="border-4 border-black rounded-md p-1 w-1/2 flex justify-between pl-4">
            <p className="w-5/6 truncate">{invitationCode}</p>
            <CopyToClipboard text={invitationCode}>
              <button className="bg-gray-300 px-4 w-20">복사</button>
            </CopyToClipboard>
          </div>
        </div>
      </div>
    </>
  );
}
