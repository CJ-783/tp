| **Action**                          | **Format**       | **Example**                                                               |
|-------------------------------------|------------------|---------------------------------------------------------------------------|
| **Help**                            | `help`           | —                                                                         |
| **List**                            | `list`           | —                                                                         |
| **Add**                             | `add n/NAME p/PHONE [e/EMAIL] [t/TAG]…​ [proj/PROJECT]…​` | `add n/James Ho p/91234567 e/jamesho@example.com t/friend proj/project-x` |
| **Edit**                            | `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL]` | `edit 2 n/James Lee p/91231234 e/jameslee@example.com`                    |
| **Find**                            | `find (n/NAME [NAME]…​ | p/PHONE [PHONE]…​)`                                                       | `find n/James Jake` or `find p/87487765 88888888`                             |
| **Delete**                          | `delete (INDEX ​ | ​ p/PHONE)`                                                               | `delete 3` or `delete p/91231234`                                      |
| **Tag**                             | `tag p/PHONE (t/TAG ​ | ​  proj/PROJECT) [t/TAG]…​ [proj/PROJECT]…​`                              | `tag p/91234567 t/bestie proj/project-x`                               |
| **Untag**                           | `untag p/PHONE (t/TAG ​ | ​ proj/PROJECT) [t/TAG]…​ [proj/PROJECT]…​​`                              | `untag p/91234567 t/bestie proj/project-x`                             |
| **Set Status**                      | `setstatus INDEX proj/PROJECT [pay/PAYMENT] [by/DEADLINE] [prog/PROGRESS]` | `setstatus 1 proj/milestone pay/paid by/03 Apr 2025 2359 prog/complete`   |
| **Save**                            | `save [FILENAME]` | `save newfile`                                                            |
| **Snapshot**                        | `snapshot`       | —                                                                         |
| **Switch Preferred Contact Method** | `switchcontact p/PHONE` | `switchcontact p/91234567`                                                |
| **Clear**                           | `clear`          | —                                                                         |
| **Exit**                            | `exit`           | —                                                                         |
For further information, go to: https://ay2425s2-cs2103-f10-1.github.io/tp/UserGuide.html
