import { useState, useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom';
import { CopyToClipboard } from 'react-copy-to-clipboard';
import axios from '../api/axios.jsx';
import { getFamilies } from '../store/user.jsx';
import { setFamily } from '../store/family';

export default function SettingPage() {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const family = useSelector((state) => state.family.value);
  const user = useSelector((state) => state.user.value);
  const [isChanged, setIsChanged] = useState(true);
  const [tabIndex, setTabIndex] = useState(0);
  const [familyName, setFamilyName] = useState(family.name);
  const [nickname, setNickname] = useState(user.nickname);
  const [families, setFamilies] = useState(user.familyInfo);
  const [selectedFamilyId, setSelectedFamilyId] = useState(family.id);
  const [motto, setMotto] = useState(family.motto);
  const [invitationCode, setInvitationCode] = useState(family.invitationCode);
  const [newLeaderMemberId, setNewLeaderMemberId] = useState(null);
  const [isLeader, setIsLeader] = useState(false);
  const [familyMembers, setFamilyMembers] = useState([
    {
      memberId: 0,
      nickname: '',
      role: '',
      me: false,
    },
  ]);

  useEffect(() => {
    axios
      .get(`family/${family.id}/familyMembers`)
      .then((response) => {
        setFamilyMembers(response.data);
        for (const member of response.data) {
          if (member.role === 'LEADER' && member.me === true) {
            setIsLeader(true);
            break;
          }
        }
      })
      .catch((error) => {
        console.log(error);
      });
    axios.get('/member/family').then((res) => {
      dispatch(getFamilies({ familyInfo: res.data }));
      setFamilies(res.data);
    });
    axios.get(`/family/${family.id}`).then((response) => {
      const familyData = {
        id: response.data.id,
        name: response.data.name,
        motto: response.data.motto,
        tree: response.data.tree,
        invitationCode: response.data.invitationCode,
        challengesIds: response.data.challengesIds,
        mainSprint: response.data.mainSprint,
        fruitSkins: response.data.fruitSkins,
      };
      dispatch(setFamily(familyData));
    });
  }, [isChanged, isLeader]);

  const handleMandate = () => {
    axios
      .put(`/family/${family.id}/mandate`, {
        newLeaderMemberId: newLeaderMemberId,
      })
      .then((response) => {
        setIsChanged(!isChanged);
        setIsLeader(false);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const handleFamilyNameChange = (e) => {
    setFamilyName(e.target.value);
  };

  const handleMottoChange = (e) => {
    setMotto(e.target.value);
  };
  const handleMotto = (e) => {
    axios
      .patch(`family/${family.id}/motto`, {
        newMotto: motto,
      })
      .then((response) => {
        setIsChanged(!isChanged);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const handleNewLeader = (e) => {
    setNewLeaderMemberId(e.target.value);
  };

  const handleNicknameChange = (e) => {
    setNickname(e.target.value);
  };

  const handleFamilyName = (e) => {
    axios
      .patch(`family/${family.id}/name`, {
        newName: familyName,
      })
      .then((response) => {
        setIsChanged(!isChanged);
      })
      .catch((error) => {
        console.log(error);
      });
  };
  const handleNickname = (e) => {
    axios
      .put('/member', {
        newNickname: nickname,
      })
      .then((response) => {
        setIsChanged(!isChanged);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const handleStart = () => {
    navigate(`/tree/${selectedFamilyId}`);
  };
  return (
    <>
      <h1>설정 페이지</h1>
      <p>
        <button
          onClick={() => setTabIndex(0)}
          className={
            tabIndex === 0 ? 'bg-gray-300 px-4 py-2 rounded-md' : 'px-4 py-2'
          }
        >
          가족
        </button>
        |
        <button
          onClick={() => setTabIndex(1)}
          className={
            tabIndex === 1 ? 'bg-gray-300 px-4 py-2  rounded-md' : 'px-4 py-2'
          }
        >
          개인
        </button>
      </p>
      {/* 가족 설정 탭 */}
      {tabIndex === 0 ? (
        <div className="w-full h-5/6 m-auto pt-10">
          <div className="w-full flex justify-around mb-10 h-12">
            <div className="rounded-md px-4 w-full pl-4 flex justify-center items-center">
              {familyMembers.map((member) => (
                <>
                  <div className="bg-gray-300 rounded-3xl px-2 mx-2">
                    {member.role === 'LEADER' ? '👑 ' : ''}
                    {member.nickname}
                  </div>
                </>
              ))}
            </div>
          </div>

          <div className="w-full flex justify-around items-center mb-10 h-12">
            <p className="w-1/4">가족 이름</p>
            <div className="border-4 border-black rounded-md p-1 w-1/2 pl-4 flex justify-between h-full">
              <input
                value={familyName}
                type="text"
                onChange={handleFamilyNameChange}
                className="w-5/6"
              />
              <button
                onClick={handleFamilyName}
                className="bg-gray-300 px-4 w-20"
              >
                저장
              </button>
            </div>
          </div>

          <div className="w-full flex justify-around items-center mb-10 h-12">
            <p className="w-1/4">가훈</p>
            <div className="border-4 border-black rounded-md p-1 w-1/2 pl-4 flex justify-between h-full">
              <input
                type="text"
                value={motto}
                onChange={handleMottoChange}
                className="w-5/6"
              />
              <button onClick={handleMotto} className="bg-gray-300 px-4 w-20">
                저장
              </button>
            </div>
          </div>

          <div className="w-full flex justify-around items-center mb-10 h-12">
            <p className="w-1/4">초대코드</p>
            <div className="border-4 border-black rounded-md p-1 w-1/2 flex justify-between pl-4 h-full">
              <p className="w-5/6 truncate text-left">{invitationCode}</p>
              <CopyToClipboard text={invitationCode}>
                <button className="bg-gray-300 px-4 w-20">복사</button>
              </CopyToClipboard>
            </div>
          </div>
          {isLeader && (
            <div className="w-full flex justify-around items-center mb-10 h-12">
              <p className="w-1/4">가장 위임</p>
              <div className="border-4 border-black rounded-md p-1 w-1/2 pl-4 flex justify-between h-full">
                <select name="" className="w-5/6" onChange={handleNewLeader}>
                  {/* <option value="" className="w-full"></option> */}
                  {familyMembers.map((member, index) =>
                    member.role === 'LEADER' ? null : (
                      <>
                        <option value={member.memberId} className="w-full">
                          {member.nickname}
                        </option>
                      </>
                    )
                  )}
                </select>
                <button
                  onClick={handleMandate}
                  className="bg-gray-300 px-4 w-20"
                >
                  위임
                </button>
              </div>
            </div>
          )}
        </div>
      ) : (
        <div className="w-full h-5/6 m-auto pt-10">
          <div className="w-full flex justify-around items-center mb-10 h-12">
            <p className="w-1/4">닉네임</p>
            <div className="border-4 border-black rounded-md p-1 w-1/2 pl-4 flex justify-between h-full">
              <input
                type="text"
                value={nickname}
                onChange={handleNicknameChange}
                className="w-5/6"
              />
              <button
                onClick={handleNickname}
                className="bg-gray-300 px-4 w-20"
              >
                저장
              </button>
            </div>
          </div>
          <div className="w-full flex justify-around items-center mb-10 h-12">
            <p className="w-1/4">가족 선택</p>
            <div className="border-4 border-black rounded-md p-1 w-1/2 pl-4 flex justify-between h-full">
              <select
                id="family_select"
                value={selectedFamilyId}
                onChange={(e) => setSelectedFamilyId(e.target.value)}
                className="w-5/6"
              >
                {families.map((family, index) => (
                  <option
                    key={index}
                    value={family.familyId}
                    className="w-full"
                  >
                    {family.name}
                  </option>
                ))}
              </select>
              <button onClick={handleStart} className="bg-gray-300 px-4 w-20">
                이동
              </button>
            </div>
          </div>

          <Link to="/createtree">가족 생성하기</Link>
        </div>
      )}
    </>
  );
}
