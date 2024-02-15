import { useState, useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { CopyToClipboard } from 'react-copy-to-clipboard';
import axios from '../api/axios.jsx';
import { getFamilies, getUser } from '../store/user.jsx';
import fruitImages from '../api/fruitImages.jsx';
import { setFamily } from '../store/family';
import CommonModal from '../components/common/CommonModal.jsx';
import SmallButton from '../components/button/SmallButton.jsx';

const fruitName = {
  ALPHABET_A: '수박',
  ALPHABET_B: '배',
  ALPHABET_C: '자두',
  ALPHABET_D: '복숭아',
  ALPHABET_E: '바나나',
  ALPHABET_F: '감',
  ALPHABET_G: '귤',
  ALPHABET_H: '망고',
  ALPHABET_I: '오렌지',
  ALPHABET_J: '키위',
  ALPHABET_K: '레몬',
  ALPHABET_L: '적포도',
  ALPHABET_N: '체리',
  ALPHABET_M: '블루베리',
  ALPHABET_O: '포도',
  ALPHABET_P: '청포도',
  ALPHABET_Q: '파인애플',
  ALPHABET_R: '두리안',
  ALPHABET_S: '풋사과',
  ALPHABET_T: '고무고무열매',
  ALPHABET_U: '이글이글열매',
};

export default function SettingPage() {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const family = useSelector((state) => state.family.value);
  const user = useSelector((state) => state.user.value);
  const [previewImage, setPreviewImage] = useState(family.profileDto.location);
  const [familyImage, setFamilyImage] = useState(null);
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
  const [dailyFruit, setDailyFruit] = useState(family.fruitSkins.daily);
  const [eventFruit, setEventFruit] = useState(family.fruitSkins.event);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isChangeFruit, setIsChangeFruit] = useState(false);
  const [isCopy, setIsCopy] = useState(false);
  const [fruitError, setFruitError] = useState('');
  const [nicknameError, setNicknameError] = useState('');
  const [familyError, setFamilyError] = useState('');
  const [challengeFruit, setChallengeFruit] = useState(family.fruitSkins.challenge);
  const [familyItem, setFamilyItem] = useState([
    {
      itemCode: '',
      type: '',
    },
  ]);
  const [familyMembers, setFamilyMembers] = useState([
    {
      memberId: 0,
      nickname: '',
      role: '',
      me: false,
    },
  ]);

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  const openCopyModal = () => {
    setIsCopy(true);
  };

  const closeCopyModal = () => {
    setIsCopy(false);
  };

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

    axios.get(`/family/${family.id}/inventory/${family.mainSprint.sprintId}`).then((res) => {
      setFamilyItem(res.data.familyItemList);
    });
    axios.get(`/family/${family.id}`).then((response) => {
      const familyData = {
        id: response.data.id,
        name: response.data.name,
        motto: response.data.motto,
        profileDto: response.data.profileDto,
        tree: response.data.tree,
        invitationCode: response.data.invitationCode,
        challengesIds: response.data.challengesIds,
        mainSprint: response.data.mainSprint,
        fruitSkins: response.data.fruitSkins,
      };
      dispatch(setFamily(familyData));
    });
    axios
      .get('/member/me')
      .then((res) => {
        dispatch(getUser(res.data));
      })
      .catch((err) => {
        console.log(err);
      });
  }, [isChanged, isLeader, isChangeFruit]);

  const handleMandate = () => {
    axios
      .put(`/family/${family.id}/mandate`, {
        newLeaderMemberId: newLeaderMemberId,
      })
      .then((response) => {
        setIsChanged(!isChanged);
        setIsLeader(false);
        openModal();
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
    if (motto.length > 30) {
      setFamilyError('가훈은 최대 30글자입니다.');
      return;
    }

    axios
      .patch(`family/${family.id}/motto`, {
        newMotto: motto,
      })
      .then((response) => {
        setIsChanged(!isChanged);
        setFamilyError('');
        openModal();
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
    if (familyName.length > 8) {
      setFamilyError('가족 이름은 최대 8글자입니다.');
      return;
    }

    if (!familyName) {
      setFamilyError('가족 이름을 입력해주세요.');
      return;
    }

    axios
      .patch(`family/${family.id}/name`, {
        newName: familyName,
      })
      .then((response) => {
        setIsChanged(!isChanged);
        setFamilyError('');
        openModal();
      })
      .catch((error) => {
        console.log(error);
      });
  };
  const handleNickname = (e) => {
    if (nickname.length > 10) {
      setNicknameError('닉네임은 최대 10글자입니다.');
      return;
    }

    axios
      .put('/member', {
        newNickname: nickname,
      })
      .then((response) => {
        setIsChanged(!isChanged);
        setNicknameError('');
        openModal();
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const handleStart = () => {
    navigate(`/tree/${selectedFamilyId}`);
  };

  const FruitChange = () => {
    if (dailyFruit === eventFruit || eventFruit === challengeFruit || challengeFruit === dailyFruit) {
      setFruitError('과일은 중복선택 할수 없습니다.');
      return;
    }

    const formData = {
      daily: dailyFruit,
      event: eventFruit,
      challenge: challengeFruit,
    };
    axios
      .put(`/family/${family.id}/fruit-skin`, formData)
      .then((res) => {
        setIsChangeFruit(!isChangeFruit);
        setFruitError('');
        openModal();
      })
      .catch((error) => {
        console.log(error);
      });
  };
  const handleFileChange = (e) => {
    const file = e.target.files[0];

    if (file) {
      const reader = new FileReader();

      reader.onloadend = () => {
        setPreviewImage(reader.result);
        setFamilyImage(file);
      };
      reader.readAsDataURL(file);
    }
  };

  const handleFamilyImage = () => {
    if (!familyImage) {
      setFamilyError('가족 사진을 선택해주세요.');
      return;
    }

    const formData = new FormData();
    formData.append('newImage', familyImage);
    axios
      .patch(`/family/${family.id}/image`, formData, {
        headers: { 'Content-Type': 'multipart/form-data' },
      })
      .then((response) => {
        setFamilyError('');
        openModal();
      })
      .catch((error) => {
        console.log(error);
      });
  };
  return (
    <>
      <p className="pt-4">
        <button
          onClick={() => setTabIndex(0)}
          className={tabIndex === 0 ? 'bg-gray-300 px-4 py-2 rounded-md transition-all duration-300' : 'px-4 py-2'}
        >
          가족
        </button>
        |
        <button
          onClick={() => setTabIndex(1)}
          className={tabIndex === 1 ? 'bg-gray-300 px-4 py-2  rounded-md transition-all duration-300' : 'px-4 py-2'}
        >
          열매
        </button>
        |
        <button
          onClick={() => setTabIndex(2)}
          className={tabIndex === 2 ? 'bg-gray-300 px-4 py-2  rounded-md transition-all duration-300' : 'px-4 py-2'}
        >
          개인
        </button>
      </p>

      <div className="w-full flex justify-around h-12">
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

      {/* 가족 설정 탭 */}
      {tabIndex === 0 ? (
        <div>
          <div className="w-full h-5/6 m-auto">
            <div className="flex h-1/3 w-full justify-center pb-2">
              <img src={previewImage} alt="미리보기" className="h-40 w-40 object-cover rounded-md" />
            </div>
            <div className="w-full flex justify-around items-center mb-4 h-12">
              <p className="w-1/4">가족 대표 사진</p>
              <div className="border-4 border-black rounded-md p-1 w-1/2 pl-4 flex justify-between h-full text-left">
                <input type="file" className="w-5/6" onChange={handleFileChange} />
                <button onClick={handleFamilyImage} className="bg-gray-300 px-4 w-20">
                  저장
                </button>
              </div>
            </div>

            <div className="w-full flex justify-around items-center mb-4 h-12">
              <p className="w-1/4">가족 이름</p>
              <div className="border-4 border-black rounded-md p-1 w-1/2 pl-4 flex justify-between h-full">
                <input value={familyName} type="text" onChange={handleFamilyNameChange} className="w-5/6" />
                <button onClick={handleFamilyName} className="bg-gray-300 px-4 w-20">
                  저장
                </button>
              </div>
            </div>

            <div className="w-full flex justify-around items-center mb-4 h-12">
              <p className="w-1/4">가훈</p>
              <div className="border-4 border-black rounded-md p-1 w-1/2 pl-4 flex justify-between h-full">
                <input type="text" value={motto} onChange={handleMottoChange} className="w-5/6" />
                <button onClick={handleMotto} className="bg-gray-300 px-4 w-20">
                  저장
                </button>
              </div>
            </div>

            <div className="w-full flex justify-around items-center mb-4 h-12">
              <p className="w-1/4">초대코드</p>
              <div className="border-4 border-black rounded-md p-1 w-1/2 flex justify-between pl-4 h-full">
                <p className="w-5/6 truncate text-left">{invitationCode}</p>
                <CopyToClipboard text={invitationCode}>
                  <button className="bg-gray-300 px-4 w-20" onClick={openCopyModal}>
                    복사
                  </button>
                </CopyToClipboard>
              </div>
            </div>
            {isLeader && (
              <div className="w-full flex justify-around items-center h-12">
                <p className="w-1/4">가장 위임</p>
                <div className="border-4 border-black rounded-md p-1 w-1/2 pl-4 flex justify-between h-full">
                  <select name="" className="w-5/6" onChange={handleNewLeader}>
                    <option value="" className="w-full"></option>
                    {familyMembers.map((member, index) =>
                      member.role === 'LEADER' ? null : (
                        <option value={member.memberId} className="w-full" key={index}>
                          {member.nickname}
                        </option>
                      )
                    )}
                  </select>
                  <button onClick={handleMandate} className="bg-gray-300 px-4 w-20">
                    위임
                  </button>
                </div>
              </div>
            )}
            {familyError ? <p className="text-red-400 pt-2">{familyError}</p> : null}
          </div>
        </div>
      ) : null}
      {/* 열매 */}
      {tabIndex === 1 ? (
        <div className="h-5/6 pt-4">
          <div className="flex h-3/4">
            <div className="h-5/6 w-1/2 p-5">
              <div className="flex h-1/3 mb-5">
                <h1 className="text-2xl my-auto justify-center w-2/12 mr-20">일상</h1>
                <select
                  value={dailyFruit}
                  onChange={(e) => setDailyFruit(e.target.value)}
                  className="h-1/3 my-auto mr-20"
                >
                  {familyItem.map((item, index) => (
                    <option key={index} value={item.itemCode}>
                      {fruitName[item.itemCode]}
                    </option>
                  ))}
                </select>

                <img src={fruitImages[dailyFruit]} alt="dailyFruit" className=" h-28 w-28" />
              </div>
              <div className="flex h-1/3 mb-5">
                <h1 className="text-2xl my-auto justify-center w-2/12 mr-20">이벤트</h1>
                <select
                  value={eventFruit}
                  onChange={(e) => setEventFruit(e.target.value)}
                  className="h-1/3 my-auto mr-20"
                >
                  {familyItem.map((item, index) => (
                    <option key={index} value={item.itemCode}>
                      {fruitName[item.itemCode]}
                    </option>
                  ))}
                </select>

                <img src={fruitImages[eventFruit]} alt="eventFruit" className=" h-28 w-28" />
              </div>
              <div className="flex h-1/3 mb-5">
                <h1 className="text-2xl my-auto justify-center w-2/12 mr-20">챌린지</h1>
                <select
                  value={challengeFruit}
                  onChange={(e) => setChallengeFruit(e.target.value)}
                  className="h-1/3 my-auto mr-20"
                >
                  {familyItem.map((item, index) => (
                    <option key={index} value={item.itemCode}>
                      {fruitName[item.itemCode]}
                    </option>
                  ))}
                </select>

                <img src={fruitImages[challengeFruit]} alt="challengeFruit" className=" h-28 w-28" />
              </div>
            </div>
            <div className="w-1/2 h-5/6 p-5 pt-0">
              <p>획득한 과일 목록</p>
              <div className="flex flex-wrap justify-center bg-gray-200 h-full overflow-y-scroll">
                {familyItem.map((item, index) => (
                  <img key={index} src={fruitImages[item.itemCode]} alt="" className="h-28 w-28 m-4" />
                ))}
              </div>
            </div>
          </div>
          <button onClick={FruitChange} className="bg-gray-300 px-4 py-2 rounded-md m-4">
            저장
          </button>
          {fruitError ? <p className=" text-red-400">{fruitError}</p> : null}
        </div>
      ) : null}

      {/* 개인 */}
      {tabIndex === 2 ? (
        <div className="w-full h-5/6 m-auto pt-24">
          <div className="w-full flex justify-around items-center mb-10 h-12">
            <p className="w-1/4">닉네임</p>
            <div className="border-4 border-black rounded-md p-1 w-1/2 pl-4 flex justify-between h-full">
              <input type="text" value={nickname} onChange={handleNicknameChange} className="w-5/6" />
              <button onClick={handleNickname} className="bg-gray-300 px-4 w-20">
                저장
              </button>
            </div>
          </div>
          <div className="w-full flex justify-around items-center mb-4 h-12">
            <p className="w-1/4">가족 선택</p>
            <div className="border-4 border-black rounded-md p-1 w-1/2 pl-4 flex justify-between h-full">
              <select
                id="family_select"
                value={selectedFamilyId}
                onChange={(e) => setSelectedFamilyId(e.target.value)}
                className="w-5/6"
              >
                {families.map((family, index) => (
                  <option key={index} value={family.familyId} className="w-full">
                    {family.name}
                  </option>
                ))}
              </select>
              <button onClick={handleStart} className="bg-gray-300 px-4 w-20">
                이동
              </button>
            </div>
          </div>
          <div className="pt-40">
            <SmallButton text="가족 생성하기" url="/createtree"></SmallButton>
          </div>
          {nicknameError ? <p className=" text-red-400">{nicknameError}</p> : null}
        </div>
      ) : null}
      <CommonModal title="변경사항" content="저장되었습니다!" isOpen={isModalOpen} closeModal={closeModal} />
      <CommonModal
        title="가족코드 복사"
        content="가족코드가 복사되었습니다."
        isOpen={isCopy}
        closeModal={closeCopyModal}
      />
    </>
  );
}
